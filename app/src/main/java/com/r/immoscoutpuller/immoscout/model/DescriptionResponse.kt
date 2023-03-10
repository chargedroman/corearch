package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class DescriptionResponse(
    @SerializedName("text") val text: String?
): Serializable
