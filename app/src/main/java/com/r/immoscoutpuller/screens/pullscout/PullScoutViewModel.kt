package com.r.immoscoutpuller.screens.pullscout

import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.screens.basepull.PullViewModel
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class PullScoutViewModel : PullViewModel<PresentableImmoScoutItem>() {

    override fun getFreshItems(): Flow<List<PresentableImmoScoutItem>> {
        return immoRepository.getImmoScoutApartmentsWeb()
    }

    override fun getItems(): Flow<List<PresentableImmoScoutItem>> {
        return immoRepository.getImmoScoutApartmentsCache()
    }

    override fun getItemsLastCacheUpdate(): Date {
        return immoRepository.getLastCacheUpdateScout()
    }

}
