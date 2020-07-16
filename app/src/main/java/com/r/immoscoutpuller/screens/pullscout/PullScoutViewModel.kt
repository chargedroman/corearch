package com.r.immoscoutpuller.screens.pullscout

import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.screens.basepull.PullViewModel
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class PullScoutViewModel : PullViewModel<PresentableImmoScoutItem>() {

    override fun getFreshItems(): Flow<List<PresentableImmoScoutItem>> {
        return immoRepository.getImmoScoutApartmentsWeb()
    }

}
