package com.r.immoscoutpuller.background

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.getApartmentsRequestSettings
import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import com.r.immoscoutpuller.notifications.NotificationModel
import com.r.immoscoutpuller.notifications.NotificationRepository
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.utility.TextLocalization
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 15.07.20
 */

class NotificationHelperSummary: KoinComponent {

    private val localRepository: LocalRepository by inject()
    private val textLocalization: TextLocalization by inject()
    private val notificationRepository: NotificationRepository by inject()


    fun onStart() {
        showProgressItemNotification()
    }

    fun onDone(diff: ImmoListDiffer.Diff) {
        if(diff.noChanges()) {
            cancelSummaryNotification()
        } else {
            showDoneItemNotification()
        }
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

}