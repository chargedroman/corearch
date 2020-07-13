package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class PagingResponse(
    @SerializedName("description") val description: DescriptionResponse
)

data class DescriptionResponse(
    @SerializedName("text") val text: String
)
