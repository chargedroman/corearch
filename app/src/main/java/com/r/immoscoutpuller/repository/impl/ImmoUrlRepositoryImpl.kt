package com.r.immoscoutpuller.repository.impl

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.r.immoscoutpuller.model.ImmoItem
import com.r.immoscoutpuller.model.PresentableImmoWeltItem
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
        //https://www.immowelt.de/liste/mainz-altstadt/wohnungen/mieten?geoid=10807315000015%2C10807315000016&roomi=1&rooma=3&primi=500&prima=800&wflmi=40&wflma=80&cp=1

        val webUrl = textLocalization.getString(R.string.immo_welt_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addPathSegment("liste")
        builder.addPathSegment(request.city)
        builder.addPathSegment("wohnungen")
        builder.addPathSegment("mieten")

        builder.addQueryParameter("geoid", request.geoCodes)
        builder.addQueryParamIfNotZero("roomi", request.minNumberOfRooms)
        builder.addQueryParamIfNotZero("rooma", request.maxNumberOfRooms)
        builder.addQueryParamIfNotZero("primi", request.minPrice)
        builder.addQueryParamIfNotZero("prima", request.maxPrice)
        builder.addQueryParamIfNotZero("wflmi", request.minLivingSpace)
        builder.addQueryParamIfNotZero("wflma", request.maxLivingSpace)
        builder.addQueryParameter("cp", pageNumber.toString())

        return builder.build()
    }


    private fun HttpUrl.Builder.addQueryParamIfNotZero(key: String, value: Double): HttpUrl.Builder {
        if(value != 0.0) {
            return addQueryParameter(key, value.toString())
        }

        return this
    }


    override fun getApartmentUrl(item: ImmoItem): HttpUrl {

        if(item is PresentableImmoWeltItem) {
            return getImmoWeltApartmentUrl(item.id)
        }

        return getImmoScoutApartmentUrl(item.id)
    }



    override fun getImmoScoutExposeUrl(itemId: String): HttpUrl {
        return getImmoScoutApartmentUrl(itemId)
    }

    override fun getImmoWeltExposeUrl(itemId: String): HttpUrl {
        return getImmoWeltApartmentUrl(itemId)
    }

    private fun getImmoWeltApartmentUrl(itemId: String): HttpUrl {
        val webUrl = textLocalization.getString(R.string.immo_welt_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addPathSegment("expose")
        builder.addPathSegment(itemId)

        return builder.build()
    }


    private fun getImmoScoutApartmentUrl(itemId: String): HttpUrl {
        val webUrl = textLocalization.getString(R.string.immo_scout_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addPathSegment("expose")
        builder.addPathSegment(itemId)

        return builder.build()
    }

}
