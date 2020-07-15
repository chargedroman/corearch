package com.r.immoscoutpuller.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.ImmoScoutRepository
import com.r.immoscoutpuller.immoscout.ImmoScoutUrlBuilder
import com.r.immoscoutpuller.immoscout.getApartmentsRequestSettings
import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
import com.r.immoscoutpuller.notifications.NotificationModel
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
    val urlBuilder: ImmoScoutUrlBuilder by inject()
    val notificationRepository: NotificationRepository by inject()

    override val coroutineContext: CoroutineDispatcher get() = Dispatchers.IO


    @ExperimentalCoroutinesApi
    override suspend fun doWork(): Result = coroutineScope {

        val differ = ImmoListDiffer()

        val job = getLastItems()
            .flatMapConcat { differ.lastItems = it; getFreshItems() }
            .flatMapConcat { differ.freshItems = it; getDiff(differ) }
            .flatMapConcat { showNotificationsForEach(it) }
            .onStart { showProgressItemNotification() }
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

        if(diff.noChanges()) {
            cancelSummaryNotification()
        } else {
            showDoneItemNotification()
        }

        emit(Unit)
    }


    /**
     * Summary notifications for progress & done
     */

    private fun showProgressItemNotification() {
        val request = localRepository.getApartmentsRequestSettings()
        val title = textLocalization.getString(R.string.notifications_progress_title)
        val model = request.itemNotification(title, true)
        notificationRepository.showNotification(model)
    }

    private fun showDoneItemNotification() {
        val request = localRepository.getApartmentsRequestSettings()
        val title = textLocalization.getString(R.string.notifications_done_title)
        val model = request.itemNotification(title, false)
        notificationRepository.showNotification(model)
    }

    private fun cancelSummaryNotification() {
        notificationRepository.cancelNotification(NotificationModel().notificationId)
    }

    private fun RentingApartmentsRequest.itemNotification(title: String, progress: Boolean)
            : NotificationModel {

        val text = textLocalization
            .getString(R.string.notifications_summary, getPrice(), getLivingSpace(), getNumberOfRooms(), geoCodes)

        return NotificationModel(
            title = title,
            text = text,
            showProgress = progress
        )
    }

    /**
     * Item notifications for add/delete/modify
     */

    private fun showNewItemNotification(item: PresentableImmoItem) {
        val title = textLocalization.getString(R.string.notifications_item_new_title)
        val model = item.itemNotification(title)
        notificationRepository.showNotification(model)
    }

    private fun showDeletedItemNotification(item: PresentableImmoItem) {
        val title = textLocalization.getString(R.string.notifications_item_deleted_title)
        val model = item.itemNotification(title)
        notificationRepository.showNotification(model)
    }

    private fun showModifiedItemNotification(item: PresentableImmoItem) {
        val title = textLocalization.getString(R.string.notifications_item_modified_title)
        val model = item.itemNotification(title)
        notificationRepository.showNotification(model)
    }

    private fun PresentableImmoItem.itemNotification(title: String): NotificationModel {

        val text = textLocalization.getString(
            R.string.notifications_item_summary,
            this.title, rooms, livingSpace, warmRent, inserted, lastModified
        )

        return NotificationModel(
            title = title,
            text = text,
            notificationId = this.pojo.id.toInt(),
            deepLinkOnClick = urlBuilder.getApartmentUrl(pojo).toString()
        )
    }

}
