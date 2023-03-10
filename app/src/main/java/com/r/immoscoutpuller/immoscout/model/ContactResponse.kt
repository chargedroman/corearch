package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class ContactResponse(
    @SerializedName("firstname") val firstName: String?,
    @SerializedName("lastname") val lastName: String?,
    @SerializedName("salutation") val salutation: String?
): Serializable
