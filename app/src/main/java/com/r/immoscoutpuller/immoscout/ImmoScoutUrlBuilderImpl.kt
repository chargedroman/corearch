package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.R
import com.roman.basearch.utility.TextLocalization
import okhttp3.HttpUrl
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class ImmoScoutUrlBuilderImpl : ImmoScoutUrlBuilder, KoinComponent {

    private val textLocalization: TextLocalization by inject()


    override fun getMainzApartmentsUrl(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String,
        pageNumber: String
    ): HttpUrl {

        val webUrl = textLocalization.getString(R.string.immo_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addPathSegment("Suche")
        builder.addPathSegment("de")
        builder.addPathSegment("rheinland-pfalz")
        builder.addPathSegment("mainz")
        builder.addPathSegment("wohnung-mieten")

        builder.addQueryParameter("geocodes", "1276011021001,1276011021017,1276011021023")
        builder.addQueryParameter("price", "-$maxPrice")
        builder.addQueryParameter("livingspace", "$minLivingSpace-")
        builder.addQueryParameter("numberofrooms", "$minNumberOfRooms-")
        builder.addQueryParameter("pagenumber", pageNumber)

        return builder.build()
    }

}
