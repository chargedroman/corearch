package com.r.immoscoutpuller.screens.pullwelt

import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.screens.basepull.PullViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class PullWeltViewModel : PullViewModel<PresentableImmoScoutItem>() {

    override fun getFreshItems(): Flow<List<PresentableImmoScoutItem>> {
        return flow { emit(listOf()) }
    }

}
