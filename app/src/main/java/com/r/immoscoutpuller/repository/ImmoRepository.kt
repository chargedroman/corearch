package com.r.immoscoutpuller.repository

import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.model.PresentableImmoWeltItem
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoRepository {

    fun getImmoScoutApartmentsCache(): Flow<List<PresentableImmoScoutItem>>
    fun getImmoScoutApartmentsWeb(): Flow<List<PresentableImmoScoutItem>>

    fun getImmoWeltApartmentsCache(): Flow<List<PresentableImmoWeltItem>>
    fun getImmoWeltApartmentsWeb(): Flow<List<PresentableImmoWeltItem>>

}
