package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.RentingResponse
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutRepository {

    fun getMainzApartments(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String
    ): Flow<List<RentingResponse>>


    fun getMainzApartmentsWeb(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String
    ): Flow<List<RentingResponse>>

}
