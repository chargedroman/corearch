package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import okhttp3.HttpUrl

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutUrlBuilder {

    fun getRentableApartmentsUrl(request: RentingApartmentsRequest, pageNumber: Int): HttpUrl

}
