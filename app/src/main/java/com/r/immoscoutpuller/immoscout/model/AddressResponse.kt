package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class AddressResponse(
    @SerializedName("city") val city: String?,
    @SerializedName("houseNumber") val houseNumber: String?,
    @SerializedName("postcode") val postcode: Int?,
    @SerializedName("quarter") val quarter: String?,
    @SerializedName("street") val street: String?,
    @SerializedName("wgs84Coordinate") val wgs84Coordinate: Wgs84Coordinate?
): Serializable
