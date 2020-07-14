package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import okhttp3.HttpUrl

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutUrlBuilder {

    fun getMainzApartmentsUrl(request: RentingApartmentsRequest, pageNumber: Int): HttpUrl

}
