package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
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


    override fun getRentableApartmentsUrl(
        request: RentingApartmentsRequest,
        pageNumber: Int
    ): HttpUrl {

        val webUrl = textLocalization.getString(R.string.immo_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addPathSegment("Suche")
        builder.addPathSegment("de")
        builder.addPathSegment("wohnung-mieten")

        builder.addQueryParameter("geocodes", request.geoCodes)
        builder.addQueryParameter("price", request.getPrice())
        builder.addQueryParameter("livingspace", request.getLivingSpace())
        builder.addQueryParameter("numberofrooms", request.getNumberOfRooms())
        builder.addQueryParameter("pagenumber", pageNumber.toString())

        return builder.build()
    }


    override fun getApartmentUrl(item: ImmoItemResponse): HttpUrl {

        val webUrl = textLocalization.getString(R.string.immo_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addPathSegment("expose")
        builder.addPathSegment(item.id.toString())

        return builder.build()
    }

}
