package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutRepository {

    fun getRentableApartmentsWeb(): Flow<List<PresentableImmoScoutItem>>
    fun getRentableApartmentsWeb(request: RentingApartmentsRequest): Flow<List<PresentableImmoScoutItem>>

}
