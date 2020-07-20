package com.r.immoscoutpuller.background

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.getImmoScoutRequestSettings
import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.r.immoscoutpuller.model.ImmoItem
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

class NotificationHelperSummary<Type: ImmoItem>(titlePrefixRes: Int): KoinComponent {

    private val localRepository: LocalRepository by inject()
    private val textLocalization: TextLocalization by inject()
    private val notificationRepository: NotificationRepository by inject()

    private val prefix = textLocalization.getString(titlePrefixRes)+" : "


    fun onStart() {
        showProgressItemNotification()
    }

    fun onDone(diff: ImmoListDiffer.Diff<Type>) {

        val cancelSummaryNotification = cancelSummaryNotification(diff)

        if(cancelSummaryNotification) {
            cancelSummaryNotification()
        } else {
            showDoneItemNotification()
        }
    }


    private fun cancelSummaryNotification(diff: ImmoListDiffer.Diff<Type>): Boolean {
        val key = textLocalization.getString(R.string.show_modified)
        val showModifiedItems = (localRepository.retrieve(key) ?: "").toBoolean()

        return if(showModifiedItems) {
            diff.noChanges()
        } else {
            diff.noChangesIgnoringModified()
        }
    }


    /**
     * Summary notifications for progress & done
     */

    private fun showProgressItemNotification() {
        val request = localRepository.getImmoScoutRequestSettings()
        val title = textLocalization.getString(R.string.notifications_progress_title)
        val model = request.itemNotification(title, true)
        notificationRepository.showNotification(model)
    }

    private fun showDoneItemNotification() {
        val request = localRepository.getImmoScoutRequestSettings()
        val title = textLocalization.getString(R.string.notifications_done_title)
        val model = request.itemNotification(title, false)
        notificationRepository.showNotification(model)
    }

    private fun cancelSummaryNotification() {
        notificationRepository.cancelNotification(prefix.hashCode())
    }

    private fun ImmoRequest.itemNotification(title: String, progress: Boolean)
            : NotificationModel {

        val text = textLocalization
            .getString(R.string.notifications_summary, getPrice(), getLivingSpace(), getNumberOfRooms(), geoCodes)
        val fullTitle = prefix + title

        return NotificationModel(
            notificationId = prefix.hashCode(),
            title = fullTitle,
            text = text,
            isSilent = true,
            showProgress = progress
        )
    }

}
