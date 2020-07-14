package com.r.immoscoutpuller.immoscout.model

import java.io.Serializable

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
): Serializable {

    fun getPrice(): String {
        return "$minPrice-"+toStringOrEmptyIfZero(maxPrice)
    }

    fun getLivingSpace(): String {
        return "$minLivingSpace-"+toStringOrEmptyIfZero(maxLivingSpace)
    }

    fun getNumberOfRooms(): String {
        return "$minNumberOfRooms-"+toStringOrEmptyIfZero(maxNumberOfRooms)
    }

    private fun toStringOrEmptyIfZero(double: Double): String {
        return if(double == 0.0) "" else double.toString()
    }

}
