package com.r.immoscoutpuller.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.r.immoscoutpuller.immoscout.ImmoScoutRepository
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
import com.r.immoscoutpuller.notifications.NotificationRepository
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.utility.TextLocalization
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    val textLocalization: TextLocalization by inject()
    val immoScoutRepository: ImmoScoutRepository by inject()
    val notificationRepository: NotificationRepository by inject()

    override val coroutineContext: CoroutineDispatcher get() = Dispatchers.IO


    @ExperimentalCoroutinesApi
    override suspend fun doWork(): Result = coroutineScope {

        val differ = ImmoListDiffer()

        val job = getLastItems()
            .flatMapConcat { differ.lastItems = it; getFreshItems() }
            .flatMapConcat { differ.freshItems = it; getDiff(differ) }
            .flatMapConcat { showNotificationsForEach(it) }
            .launchIn(this)

        job.join()

        Result.success()
    }



    private fun getDiff(differ: ImmoListDiffer): Flow<ImmoListDiffer.Diff> = flow {
        emit(differ.createDiff())
    }

    private fun getLastItems(): Flow<List<PresentableImmoItem>> {
        return localRepository
            .readFile<List<PresentableImmoItem>>(KEY_IMMO_LIST)
            .catch { emit(listOf()) }
    }

    private fun getFreshItems(): Flow<List<PresentableImmoItem>> {
        return immoScoutRepository.getRentableApartmentsWeb()
    }



    private fun showNotificationsForEach(diff: ImmoListDiffer.Diff) = flow {
        diff.newItems.forEach { showNewItemNotification(it) }
        diff.deletedItems.forEach { showDeletedItemNotification(it) }
        diff.modifiedItems.forEach { showModifiedItemNotification(it) }
        emit(Unit)
    }

    private fun showNewItemNotification(item: PresentableImmoItem) {

    }

    private fun showDeletedItemNotification(item: PresentableImmoItem) {

    }

    private fun showModifiedItemNotification(item: PresentableImmoItem) {

    }

}
