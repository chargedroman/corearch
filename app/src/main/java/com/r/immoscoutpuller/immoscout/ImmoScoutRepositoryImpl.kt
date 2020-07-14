package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.model.PagingResponse
import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
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

class ImmoScoutRepositoryImpl : ImmoScoutRepository, KoinComponent {

    private val client: OkHttpClient by inject()
    private val immoScoutParser: ImmoScoutParser by inject()
    private val immoScoutUrlBuilder: ImmoScoutUrlBuilder by inject()


    override fun getRentableApartmentsWeb(request: RentingApartmentsRequest) = flow {

        val resultList = mutableListOf<PresentableImmoItem>()

        var hasNext = true
        var pageNumber = 0

        while(hasNext) {
            val next = getRentableApartmentsWeb(request, pageNumber)
            val result = next.getAllImmoItems()
            resultList.transformingAddAll(result)

            pageNumber++
            hasNext = next.paging?.numberOfPages != null && next.paging.numberOfPages > pageNumber
        }

        resultList.sortBy { -it.pojo.creation.time }

        emit(resultList)
    }


    private fun MutableList<PresentableImmoItem>.transformingAddAll(raw: List<ImmoItemResponse>) {
        for(item in raw) {
            this.add(PresentableImmoItem(item))
        }
    }

    private fun getRentableApartmentsWeb(
        request: RentingApartmentsRequest,
        pageNumber: Int
    ): PagingResponse {

        val immoWebUrl = immoScoutUrlBuilder
            .getRentableApartmentsUrl(request, pageNumber)

        val webRequest = Request.Builder()
            .url(immoWebUrl)
            .build()

        val webResponse = client.newCall(webRequest).execute()
        return immoScoutParser.extractPagingResponseFrom(webResponse)
    }



}
