package com.r.immoscoutpuller.immoscout.model

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

data class RentingApartmentsRequest(
    val minPrice: Double = 0.0,
    val maxPrice: Double = 0.0,
    val minLivingSpace: Double = 0.0,
    val maxLivingSpace: Double = 0.0,
    val minNumberOfRooms: Double = 0.0,
    val maxNumberOfRooms: Double = 0.0,
    val geoCodes: String = ""
) {

    fun getPrice(): String {
        return "$minPrice-$maxPrice"
    }

    fun getLivingSpace(): String {
        return "$minLivingSpace-$maxLivingSpace"
    }

    fun getNumberOfRooms(): String {
        return "$minNumberOfRooms-$maxNumberOfRooms"
    }

}
