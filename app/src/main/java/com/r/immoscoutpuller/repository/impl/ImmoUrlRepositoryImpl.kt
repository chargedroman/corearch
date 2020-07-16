package com.r.immoscoutpuller.repository.impl

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.r.immoscoutpuller.model.ImmoItem
import com.r.immoscoutpuller.repository.ImmoUrlRepository
import com.roman.basearch.utility.TextLocalization
import okhttp3.HttpUrl
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class ImmoUrlRepositoryImpl : ImmoUrlRepository, KoinComponent {

    private val textLocalization: TextLocalization by inject()


    override fun getImmoScoutUrl(
        request: ImmoRequest,
        pageNumber: Int
    ): HttpUrl {

        val webUrl = textLocalization.getString(R.string.immo_scout_base_web_url)
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


    override fun getImmoWeltUrl(request: ImmoRequest, pageNumber: Int): HttpUrl {
        TODO("Not yet implemented")
    }


    override fun getApartmentUrl(item: ImmoItem): HttpUrl {

        return getImmoScoutApartmentUrl(item)
    }


    private fun getImmoWeltApartmentUrl(item: ImmoItem): HttpUrl {
        val webUrl = textLocalization.getString(R.string.immo_welt_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addPathSegment("expose")
        builder.addPathSegment(item.id.toString())

        return builder.build()
    }


    private fun getImmoScoutApartmentUrl(item: ImmoItem): HttpUrl {
        val webUrl = textLocalization.getString(R.string.immo_scout_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addPathSegment("expose")
        builder.addPathSegment(item.id.toString())

        return builder.build()
    }

}
