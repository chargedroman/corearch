package com.r.immoscoutpuller.background.worker

import android.content.Context
import androidx.work.WorkerParameters
import com.r.immoscoutpuller.immoscout.IMMO_WELT_ITEMS
import com.r.immoscoutpuller.model.PresentableImmoWeltItem
import com.r.immoscoutpuller.repository.ImmoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class ImmoWeltPullWorker(context: Context, params: WorkerParameters)
    : AbstractPullWorker<PresentableImmoWeltItem>(context, params) {

    override val keyImmoList: String = IMMO_WELT_ITEMS

    private val immoRepository: ImmoRepository by inject()

    override fun getFreshImmoItems(): Flow<List<PresentableImmoWeltItem>> {
        return immoRepository.getImmoWeltApartmentsWeb()
    }

}
