package com.r.immoscoutpuller.immoscout

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class ImmoScoutRepositoryImpl(private val web: ImmoScoutWebRepository) : ImmoScoutRepository {


    override fun getMainzApartments(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String
    ) = flow {
        val result = web.getMainzApartments(
            price = "-$maxPrice",
            livingSpace = "$minLivingSpace-",
            numberOfRooms = "$minNumberOfRooms-")
        emit(result)
    }

}
