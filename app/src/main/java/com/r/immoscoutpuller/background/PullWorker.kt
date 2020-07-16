package com.r.immoscoutpuller.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.repository.ImmoRepository
import com.roman.basearch.utility.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PullWorker(context: Context, params: WorkerParameters)
    : CoroutineWorker(context, params), KoinComponent {

    companion object {
        const val KEY_IMMO_LIST = "immoItems"
    }

    val localRepository: LocalRepository by inject()
    val immoScoutRepository: ImmoRepository by inject()

    val notificationHelperItem = NotificationHelperItem()
    val notificationHelperSummary = NotificationHelperSummary()


    override val coroutineContext: CoroutineDispatcher get() = Dispatchers.IO


    override suspend fun doWork(): Result = coroutineScope {

        val differ = ImmoListDiffer()

        val job = getLastItems()
            .flatMapConcat { differ.lastItems = it; getFreshItems() }
            .flatMapConcat { saveFreshItems(it) }
            .flatMapConcat { differ.freshItems = it; getDiff(differ) }
            .flatMapConcat { showNotificationsForEach(it) }
            .onStart { notificationHelperSummary.onStart() }
            .launchIn(this)

        job.join()

        Result.success()
    }


    private fun getDiff(differ: ImmoListDiffer): Flow<ImmoListDiffer.Diff> = flow {
        emit(differ.createDiff())
    }

    private fun getLastItems(): Flow<List<PresentableImmoScoutItem>> {
        return localRepository
            .readFile<List<PresentableImmoScoutItem>>(KEY_IMMO_LIST)
            .catch { emit(listOf()) }
    }

    private fun getFreshItems(): Flow<List<PresentableImmoScoutItem>> {
        return immoScoutRepository.getImmoScoutApartmentsWeb()
    }

    private fun showNotificationsForEach(diff: ImmoListDiffer.Diff) = flow {
        notificationHelperItem.onDone(diff)
        notificationHelperSummary.onDone(diff)
        emit(diff)
    }

    private fun saveFreshItems(items: List<PresentableImmoScoutItem>): Flow<List<PresentableImmoScoutItem>> {
        return localRepository.saveFile(KEY_IMMO_LIST, items)
    }


}
