package com.r.immoscoutpuller.repository.impl

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.r.immoscoutpuller.model.ImmoItem
import com.r.immoscoutpuller.model.PresentableImmoWeltItem
import com.r.immoscoutpuller.repository.ImmoUrlRepository
import com.roman.basearch.utility.TextLocalization
import okhttp3.HttpUrl
import okhttp3.Request
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

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

    override fun buildWithFakeImmoScoutHeaders(request: Request.Builder): Request {
        return request
            .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .addHeader("accept-encoding", "utf-8")
            .addHeader("accept-language", "de")
            .addHeader("sec-fetch-dest", "document")
            .addHeader("sec-fetch-mode", "navigate")
            .addHeader("sec-fetch-site", "none")
            .addHeader("sec-fetch-user", "?1")
            .addHeader("upgrade-insecure-requests", "1")
            .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
            .addHeader("Cookie", getCookieString())
            .addHeader("Connection", "keep-alive")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Host", "www.immobilienscout24.de")
            .addHeader("Postman-Token", UUID.randomUUID().toString())
            .build()
    }

    private fun getCookieString(): String {
        return "reese84=3:7vSJTalSi4TMqUNSK0hCwg==:NKkUCj01QWYg+PQQHeuzq/NFz7TkZuCBnd17bt7hZSdJXI0MMuCab4QiHOickH8d+slb4pYp8nQVw8eOpLpTxja36aN8dLFp19o3R3vKnDzydRw/uzZF1Vfg2yG28gILZ8lz8pmTuNj3cTZnl0nSCYfr1yw8JoFkejminS98VAl09ukTOJfhnfWRNZada5+vf97hRKYx4099GL+enMviVhsbUYjVaVgfUwJHEVyTjFpPGVJ+HR9ZHn4LbRHq0lflR1kiLR7T//HLSFG1xHzlO3z0eJYOW/ZQ2AKoZT+gp3VkhKuN/R31jPS3ju8l9PvdG4bBoXkBZdr9AFOn/jIty/9dpscXONGDWT8eYI8CKRozmPwg8uaDKBhFDJLCQ4kJ1tM58FSUzyZ1Gk2Jfb+v6q0CkkRGxiS4NURqKlz6tvg=:1KKSXEnQjt+xL75wO3v6JcyB1Stm6D+xsax/o81fuY8=;"
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
