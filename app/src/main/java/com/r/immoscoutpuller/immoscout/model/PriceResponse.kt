package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class PriceResponse(
    @SerializedName("currency") val currency: String?,
    @SerializedName("marketingType") val marketingType: String?,
    @SerializedName("priceIntervalType") val priceIntervalType: String?,
    @SerializedName("rentScope") val rentScope: String?,
    @SerializedName("value") val value: Double?
): Serializable

data class RentResponse(
    @SerializedName("calculationMode") val calculationMode: String?,
    @SerializedName("totalRent") val totalRent: PriceResponse?,
): Serializable
