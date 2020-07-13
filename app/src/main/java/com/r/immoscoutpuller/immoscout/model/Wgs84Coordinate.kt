package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class Wgs84Coordinate(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)
