package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class PriceResponse(
    @SerializedName("currency") val currency: String,
    @SerializedName("marketingType") val marketingType: String,
    @SerializedName("priceIntervalType") val priceIntervalType: String,
    @SerializedName("rentScope") val rentScope: String?,
    @SerializedName("value") val value: Double
)
