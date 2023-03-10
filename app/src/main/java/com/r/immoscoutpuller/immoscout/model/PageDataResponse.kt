package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class PageDataResponse(
    @SerializedName("numberOfHits") val numberOfHits: Int?,
    @SerializedName("numberOfListings") val numberOfListings: Int?,
    @SerializedName("numberOfPages") val numberOfPages: Int?,
    @SerializedName("pageNumber") val pageNumber: Int?,
    @SerializedName("pageSize") val pageSize: Int?
): Serializable
