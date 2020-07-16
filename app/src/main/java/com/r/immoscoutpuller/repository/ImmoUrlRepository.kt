package com.r.immoscoutpuller.repository

import com.r.immoscoutpuller.immoscout.model.ImmoScoutRequest
import com.r.immoscoutpuller.model.ImmoItem
import okhttp3.HttpUrl

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoUrlRepository {

    fun getImmoScoutUrl(request: ImmoScoutRequest, pageNumber: Int): HttpUrl
    fun getApartmentUrl(item: ImmoItem): HttpUrl

}
