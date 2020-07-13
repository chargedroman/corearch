package com.r.immoscoutpuller.immoscout

import okhttp3.HttpUrl

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutUrlBuilder {

    fun getMainzApartmentsUrl(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String,
        pageNumber: String
    ): HttpUrl

}
