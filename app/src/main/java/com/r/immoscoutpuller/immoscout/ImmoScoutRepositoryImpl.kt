package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.model.PagingResponse
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
import kotlinx.coroutines.flow.*
import okhttp3.*
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


    override fun getMainzApartmentsWeb(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String
    ) = flow {

        val resultList = mutableListOf<PresentableImmoItem>()

        var hasNext = true
        var pageNumber = 0

        while(hasNext) {
            val next = getMainzApartmentsWeb(maxPrice, minLivingSpace, minNumberOfRooms, pageNumber.toString())
            val result = next.getAllImmoItems()
            resultList.transformingAddAll(result)

            pageNumber++
            hasNext = next.paging?.numberOfPages != null && next.paging.numberOfPages > pageNumber
        }

        emit(resultList)
    }


    private fun MutableList<PresentableImmoItem>.transformingAddAll(raw: List<ImmoItemResponse>) {
        for(item in raw) {
            this.add(PresentableImmoItem(item))
        }
    }

    private fun getMainzApartmentsWeb(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String,
        pageNumber: String
    ): PagingResponse {

        val immoWebUrl = immoScoutUrlBuilder
            .getMainzApartmentsUrl(maxPrice, minLivingSpace, minNumberOfRooms, pageNumber)

        val request = Request.Builder()
            .url(immoWebUrl)
            .build()

        val webResponse = client.newCall(request).execute()
        return immoScoutParser.extractPagingResponseFrom(webResponse)
    }



}
