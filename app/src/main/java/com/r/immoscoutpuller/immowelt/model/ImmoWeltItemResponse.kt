package com.r.immoscoutpuller.immowelt.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

data class ImmoWeltItemResponse(
    @SerializedName("id") var id: String? = null,
    @SerializedName("Title") val title: String,
    @SerializedName("Bild") val image: String,
    @SerializedName("ZimmerAnzahl") val rooms: String,
    @SerializedName("Preis") val price: String,
    @SerializedName("Wohnflaeche") val livingSpace: String
): Serializable
