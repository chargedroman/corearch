package com.r.immoscoutpuller.repository

import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.model.ImmoScoutRequest
import okhttp3.HttpUrl

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoUrlRepository {

    fun getImmoScoutUrl(request: ImmoScoutRequest, pageNumber: Int): HttpUrl
    fun getImmoScoutApartmentUrl(item: ImmoItemResponse): HttpUrl

}
