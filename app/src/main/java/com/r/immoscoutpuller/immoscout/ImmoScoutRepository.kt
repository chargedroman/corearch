package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.model.PagingResponse
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutRepository {

    fun getMainzApartmentsWeb(
        maxPrice: String,
        minLivingSpace: String,
        minNumberOfRooms: String
    ): Flow<List<ImmoItemResponse>>

}
