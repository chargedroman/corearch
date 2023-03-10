package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class ImmoItemResponse(
    @SerializedName("@id") val id: Long?,
    @SerializedName("@creation") val creation: Date?,
    @SerializedName("@modification") val modification: Date?,
    @SerializedName("@publishDate") val publishDate: Date?,
    @SerializedName("realEstateId") val realEstateId: Long?,
    @SerializedName("resultlist.realEstate") val details: ImmoItemDetailResponse?
): Serializable
