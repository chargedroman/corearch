package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class ImmoItemDetailResponse(
    @SerializedName("@id") val id: Long,
    @SerializedName("@xsi.type") val type: String,
    @SerializedName("address") val address: AddressResponse,
    @SerializedName("balcony") val balcony: Boolean,
    @SerializedName("builtInKitchen") val builtInKitchen: Boolean,
    @SerializedName("floorplan") val floorplan: Boolean,
    @SerializedName("garden") val garden: Boolean,
    @SerializedName("privateOffer") val privateOffer: Boolean,
    @SerializedName("spotlightListing") val spotlightListing: Boolean,
    @SerializedName("streamingVideo") val streamingVideo: Boolean,
    @SerializedName("calculatedPrice") val calculatedPrice: PriceResponse,
    @SerializedName("price") val price: PriceResponse,
    @SerializedName("contactDetails") val contactDetails: ContactResponse,
    @SerializedName("listingType") val listingType: String,
    @SerializedName("livingSpace") val livingSpace: Double,
    @SerializedName("numberOfRooms") val numberOfRooms: Double,
    @SerializedName("title") val title: String
)
