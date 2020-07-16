package com.r.immoscoutpuller.background.worker

import android.content.Context
import androidx.work.WorkerParameters
import com.r.immoscoutpuller.immoscout.IMMO_SCOUT_ITEMS
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.repository.ImmoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class ImmoScoutPullWorker(context: Context, params: WorkerParameters)
    : AbstractPullWorker<PresentableImmoScoutItem>(context, params) {

    override val keyImmoList: String = IMMO_SCOUT_ITEMS

    private val immoRepository: ImmoRepository by inject()

    override fun getFreshImmoItems(): Flow<List<PresentableImmoScoutItem>> {
        return immoRepository.getImmoScoutApartmentsWeb()
    }

}
