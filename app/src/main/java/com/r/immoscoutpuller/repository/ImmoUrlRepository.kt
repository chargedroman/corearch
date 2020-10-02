package com.r.immoscoutpuller.repository

import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.r.immoscoutpuller.model.ImmoItem
import okhttp3.HttpUrl
import okhttp3.Request

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoUrlRepository {

    fun getImmoScoutUrl(request: ImmoRequest, pageNumber: Int): HttpUrl
    fun getImmoWeltUrl(request: ImmoRequest, pageNumber: Int): HttpUrl
    fun buildWithFakeImmoScoutHeaders(request: Request.Builder): Request

    fun getApartmentUrl(item: ImmoItem): HttpUrl
    fun getImmoScoutExposeUrl(itemId: String): HttpUrl
    fun getImmoWeltExposeUrl(itemId: String): HttpUrl

}
