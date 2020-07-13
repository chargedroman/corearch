package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.RentingResponse
import com.roman.basearch.utility.TextLocalization
import kotlinx.coroutines.flow.*
import okhttp3.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class ImmoScoutRepositoryImpl : ImmoScoutRepository, KoinComponent {

    private val client: OkHttpClient by inject()
    private val textLocalization: TextLocalization by inject()
    private val webRepository: ImmoScoutWebRepository by inject()


    override fun getMainzApartments(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String
    ) = flow {

        val result =
            webRepository.getMainzApartments(
                "-$maxPrice",
                "$minNumberOfRooms-",
                "$minLivingSpace-"
            )

        emit(result)
    }


    override fun getMainzApartmentsWeb(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String
    ) = flow {

        val immoWebUrl = getMainzApartmentsUrl(maxPrice, minLivingSpace, minNumberOfRooms)

        val request = Request.Builder()
            .url(immoWebUrl)
            .build()

        val webResponse = client.newCall(request).execute()
        val result = parse(webResponse)

        emit(result)
    }

    private fun parse(response: Response): List<RentingResponse> {
        val body = response.body()?.string() ?: return listOf()
        println(body)

        return listOf()
    }

    private fun getMainzApartmentsUrl(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String
    ): HttpUrl {

        val webUrl = textLocalization.getString(R.string.immo_base_web_url)
        val builder = HttpUrl.parse(webUrl)!!.newBuilder()

        builder.addQueryParameter("geocodes", "1276011021001,1276011021017,1276011021023")
        builder.addQueryParameter("price", "-$maxPrice")
        builder.addQueryParameter("livingspace", "$minLivingSpace-")
        builder.addQueryParameter("numberofrooms", "$minNumberOfRooms-")

        return builder.build()
    }

}
