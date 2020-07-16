package com.r.immoscoutpuller.repository

import com.r.immoscoutpuller.immoscout.model.ImmoScoutRequest
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoRepository {

    fun getImmoScoutApartmentsWeb(): Flow<List<PresentableImmoScoutItem>>
    fun getImmoScoutApartmentsWeb(request: ImmoScoutRequest): Flow<List<PresentableImmoScoutItem>>

}
