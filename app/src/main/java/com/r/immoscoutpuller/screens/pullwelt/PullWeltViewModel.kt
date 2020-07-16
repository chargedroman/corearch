package com.r.immoscoutpuller.screens.pullwelt

import com.r.immoscoutpuller.model.PresentableImmoWeltItem
import com.r.immoscoutpuller.screens.basepull.PullViewModel
import kotlinx.coroutines.flow.Flow

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class PullWeltViewModel : PullViewModel<PresentableImmoWeltItem>() {

    override fun getFreshItems(): Flow<List<PresentableImmoWeltItem>> {
        return immoRepository.getImmoWeltApartmentsWeb()
    }

}
