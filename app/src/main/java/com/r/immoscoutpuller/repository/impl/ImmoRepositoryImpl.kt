package com.r.immoscoutpuller.repository.impl

import android.annotation.SuppressLint
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
import com.roman.basearch.baseextensions.onErrorDo
import com.roman.basearch.utility.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class ImmoRepositoryImpl : ImmoRepository, KoinComponent {

    companion object {
        private const val IMMO_SCOUT_KEY = "IMMO_SCOUT_KEY"
        private const val IMMO_WELT_KEY = "IMMO_WELT_KEY"
        private const val IMMO_SCOUT_DATE = "IMMO_SCOUT_DATE"
        private const val IMMO_WELT_DATE = "IMMO_WELT_DATE"
    }

    private val client: OkHttpClient by inject()
    private val localRepository: LocalRepository by inject()
    private val immoScoutParser: ImmoScoutParser by inject()
    private val immoWeltParser: ImmoWeltParser by inject()
    private val immoUrlBuilder: ImmoUrlRepository by inject()
    private val fakeBrowser: FakeBrowser by inject()


    override fun getImmoScoutApartmentsCache(): Flow<List<PresentableImmoScoutItem>> {
        return localRepository
            .readFile<List<PresentableImmoScoutItem>>(IMMO_SCOUT_KEY)
            .onErrorDo(getImmoScoutApartmentsWeb())
    }

    override fun getImmoWeltApartmentsCache(): Flow<List<PresentableImmoWeltItem>> {
        return localRepository
            .readFile<List<PresentableImmoWeltItem>>(IMMO_WELT_KEY)
            .onErrorDo(getImmoWeltApartmentsWeb())
    }


    override fun getImmoScoutApartmentsWeb(): Flow<List<PresentableImmoScoutItem>> {
        val request = localRepository.getImmoScoutRequestSettings()
        return getImmoScoutApartmentsWeb(request)
            .flatMapConcat { localRepository.saveFile(IMMO_SCOUT_KEY, it) }
            .onCompletion { localRepository.save(IMMO_SCOUT_DATE, Date().time.toString()) }
    }

    override fun getImmoWeltApartmentsWeb(): Flow<List<PresentableImmoWeltItem>> {
        val request = localRepository.getImmoWeltRequestSettings()
        return getImmoWeltApartmentsWeb(request)
            .flatMapConcat { localRepository.saveFile(IMMO_WELT_KEY, it) }
            .onCompletion { localRepository.save(IMMO_WELT_DATE, Date().time.toString()) }
    }

    override fun getLastCacheUpdateScout(): Date {
        val date = localRepository.retrieve(IMMO_SCOUT_DATE)?.toLongOrNull() ?: return Date()
        return Date(date)
    }

    override fun getLastCacheUpdateWelt(): Date {
        val date = localRepository.retrieve(IMMO_WELT_DATE)?.toLongOrNull() ?: return Date()
        return Date(date)
    }


    private fun getImmoWeltApartmentsWeb(request: ImmoRequest) = flow {

        val resultList = mutableListOf<PresentableImmoWeltItem>()
        val allItemIds = getImmoWeltIds(request)

        for(itemId in allItemIds) {
            val immoWeltItem = getImmoWeltItem(itemId)
            resultList.add(PresentableImmoWeltItem(immoWeltItem))
        }

        emit(resultList)
    }


    private fun getImmoScoutApartmentsWeb(request: ImmoRequest) = flow {

        val resultList = mutableListOf<PresentableImmoScoutItem>()

        var hasNext = true
        var pageNumber = 1

        while(hasNext) {
            val next = getImmoScoutApartmentsWeb(request, pageNumber)
            val pages = next.paging?.numberOfPages
            val result = next.getAllImmoItems()
            resultList.transformingAddAll(result)

            pageNumber++
            hasNext = pages != null && pages > pageNumber
        }

        resultList.removeAll {
            val totalRent = it.pojo.details?.calculatedTotalRent?.totalRent?.value
            totalRent == null || totalRent > request.maxPrice
        }

        resultList.sortBy {
            -(it.pojo.creation?.time ?: 0)
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

    @SuppressLint("SetJavaScriptEnabled")
    private suspend fun getImmoScoutApartmentsWeb(
        request: ImmoRequest,
        pageNumber: Int
    ): PagingResponse {

        val immoWebUrl = immoUrlBuilder.getImmoScoutUrl(request, pageNumber).toString()
        val rawHtml = fakeBrowser.loadPage(immoWebUrl)
        return immoScoutParser.extractPagingResponseFrom(rawHtml)
    }

    private fun getImmoWeltItem(itemId: String): ImmoWeltItemResponse {

        val immoWebUrl = immoUrlBuilder.getImmoWeltExposeUrl(itemId)
        val webRequest = Request.Builder().url(immoWebUrl).build()
        val webResponse = client.newCall(webRequest).execute()
        return immoWeltParser.extractImmoItemFrom(itemId, webResponse)
    }



}
