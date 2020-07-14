package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutRepository {

    fun getRentableApartmentsWeb(request: RentingApartmentsRequest): Flow<List<PresentableImmoItem>>

}
