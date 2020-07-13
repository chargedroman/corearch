package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class ImmoItemsResponse(
    @SerializedName("@numberOfHits") val numberOfHits: Int,
    @SerializedName("@realEstateType") val realEstateType: Int,
    @SerializedName("resultlistEntry") val immoItems: List<ImmoItemResponse>
)
