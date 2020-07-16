package com.r.immoscoutpuller.repository

import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.model.PresentableImmoWeltItem
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoRepository {

    fun getImmoScoutApartmentsWeb(): Flow<List<PresentableImmoScoutItem>>
    fun getImmoScoutApartmentsWeb(request: ImmoRequest): Flow<List<PresentableImmoScoutItem>>

    fun getImmoWeltApartmentsWeb(): Flow<List<PresentableImmoWeltItem>>
    fun getImmoWeltApartmentsWeb(request: ImmoRequest): Flow<List<PresentableImmoWeltItem>>

}
