package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.RentingResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

@Suppress("TooManyFunctions", "LongParameterList")
interface ImmoScoutWebRepository {


    @GET("search/v1.0/search/region")
    suspend fun getMainzApartments(
        @Query("price") price: String,
        @Query("numberofrooms") numberOfRooms: String,
        @Query("livingspace") livingSpace: String,
        @Query("geocodes") geoCodes: String = "1276011021001,1276011021017,1276011021023",
        @Query("realestatetype") realEstateType: String = "APARTMENT_RENT",
        @Header("Accept") accept: String = "application/json;strict=true"
    ): List<RentingResponse>

}