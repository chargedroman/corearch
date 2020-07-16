package com.r.immoscoutpuller.repository

import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.r.immoscoutpuller.model.ImmoItem
import okhttp3.HttpUrl

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoUrlRepository {

    fun getImmoScoutUrl(request: ImmoRequest, pageNumber: Int): HttpUrl
    fun getImmoWeltUrl(request: ImmoRequest, pageNumber: Int): HttpUrl
    fun getApartmentUrl(item: ImmoItem): HttpUrl

}
