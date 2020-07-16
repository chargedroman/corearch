package com.r.immoscoutpuller.repository.impl

import com.r.immoscoutpuller.immoscout.ImmoScoutParser
import com.r.immoscoutpuller.immoscout.getApartmentsRequestSettings
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.model.ImmoScoutRequest
import com.r.immoscoutpuller.immoscout.model.PagingResponse
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
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
    private val immoScoutUrlBuilder: ImmoUrlRepository by inject()


    override fun getImmoScoutApartmentsWeb(): Flow<List<PresentableImmoScoutItem>> {
        val request = localRepository.getApartmentsRequestSettings()
        return getImmoScoutApartmentsWeb(request)
    }

    override fun getImmoScoutApartmentsWeb(request: ImmoScoutRequest) = flow {

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


    private fun MutableList<PresentableImmoScoutItem>.transformingAddAll(raw: List<ImmoItemResponse>) {
        for(item in raw) {
            this.add(PresentableImmoScoutItem(item))
        }
    }

    private fun getImmoScoutApartmentsWeb(
        request: ImmoScoutRequest,
        pageNumber: Int
    ): PagingResponse {

        val immoWebUrl = immoScoutUrlBuilder
            .getImmoScoutUrl(request, pageNumber)

        val webRequest = Request.Builder()
            .url(immoWebUrl)
            .build()

        val webResponse = client.newCall(webRequest).execute()
        return immoScoutParser.extractPagingResponseFrom(webResponse)
    }



}
