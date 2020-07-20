package com.r.immoscoutpuller.repository.impl

import com.r.immoscoutpuller.immoscout.ImmoScoutParser
import com.r.immoscoutpuller.immoscout.getImmoScoutRequestSettings
import com.r.immoscoutpuller.immoscout.getImmoWeltRequestSettings
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.r.immoscoutpuller.immoscout.model.PagingResponse
import com.r.immoscoutpuller.immowelt.ImmoWeltParser
import com.r.immoscoutpuller.immowelt.model.ImmoWeltItemResponse
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.model.PresentableImmoWeltItem
import com.r.immoscoutpuller.repository.ImmoRepository
import com.r.immoscoutpuller.repository.ImmoUrlRepository
import com.roman.basearch.utility.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class ImmoRepositoryImpl : ImmoRepository, KoinComponent {

    private val client: OkHttpClient by inject()
    private val localRepository: LocalRepository by inject()
    private val immoScoutParser: ImmoScoutParser by inject()
    private val immoWeltParser: ImmoWeltParser by inject()
    private val immoUrlBuilder: ImmoUrlRepository by inject()


    override fun getImmoScoutApartmentsWeb(): Flow<List<PresentableImmoScoutItem>> {
        val request = localRepository.getImmoScoutRequestSettings()
        return getImmoScoutApartmentsWeb(request)
    }

    override fun getImmoScoutApartmentsWeb(request: ImmoRequest) = flow {

        val resultList = mutableListOf<PresentableImmoScoutItem>()

        var hasNext = true
        var pageNumber = 0

        while(hasNext) {
            val next = getImmoScoutApartmentsWeb(request, pageNumber)
            val result = next.getAllImmoItems()
            resultList.transformingAddAll(result)

            pageNumber++
            hasNext = next.paging?.numberOfPages != null && next.paging.numberOfPages > pageNumber
        }

        resultList.sortBy { -it.pojo.creation.time }

        emit(resultList)
    }


    override fun getImmoWeltApartmentsWeb(): Flow<List<PresentableImmoWeltItem>> {
        val request = localRepository.getImmoWeltRequestSettings()
        return getImmoWeltApartmentsWeb(request)
    }


    override fun getImmoWeltApartmentsWeb(request: ImmoRequest) = flow {

        val resultList = mutableListOf<PresentableImmoWeltItem>()
        val allItemIds = getImmoWeltIds(request)

        for(itemId in allItemIds) {
            val immoWeltItem = getImmoWeltItem(itemId)
            resultList.add(PresentableImmoWeltItem(immoWeltItem))
        }

        emit(resultList)
    }


    private fun MutableList<PresentableImmoScoutItem>.transformingAddAll(raw: List<ImmoItemResponse>) {
        for(item in raw) {
            this.add(PresentableImmoScoutItem(item))
        }
    }


    private fun getImmoWeltIds(request: ImmoRequest): Set<String> {
        val allIds = mutableSetOf<String>()

        var hasNext = true
        var pageNumber = 1

        while(hasNext) {
            val next = getImmoWeltIds(request, pageNumber)
            hasNext = !(next.isEmpty() || allIds.containsAll(next))
            pageNumber++
            allIds.addAll(next)
        }

        return allIds
    }


    private fun getImmoWeltIds(
        request: ImmoRequest,
        pageNumber: Int
    ): List<String> {

        val immoWebUrl = immoUrlBuilder.getImmoWeltUrl(request, pageNumber)
        val webRequest = Request.Builder().url(immoWebUrl).build()
        val webResponse = client.newCall(webRequest).execute()
        return immoWeltParser.extractEstateIdsFrom(webResponse)
    }

    private fun getImmoScoutApartmentsWeb(
        request: ImmoRequest,
        pageNumber: Int
    ): PagingResponse {

        val immoWebUrl = immoUrlBuilder.getImmoScoutUrl(request, pageNumber)
        val webRequest = Request.Builder().url(immoWebUrl).build()
        val webResponse = client.newCall(webRequest).execute()
        return immoScoutParser.extractPagingResponseFrom(webResponse)
    }

    private fun getImmoWeltItem(itemId: String): ImmoWeltItemResponse {

        val immoWebUrl = immoUrlBuilder.getImmoWeltExposeUrl(itemId)
        val webRequest = Request.Builder().url(immoWebUrl).build()
        val webResponse = client.newCall(webRequest).execute()
        return immoWeltParser.extractImmoItemFrom(itemId, webResponse)
    }


}