package com.r.immoscoutpuller.background

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.model.ImmoItem
import com.r.immoscoutpuller.notifications.NotificationModel
import com.r.immoscoutpuller.notifications.NotificationRepository
import com.r.immoscoutpuller.repository.ImmoUrlRepository
import com.roman.basearch.utility.TextLocalization
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 15.07.20
 */

class NotificationHelperItem<Type: ImmoItem>: KoinComponent {

    private val urlBuilder: ImmoUrlRepository by inject()
    private val textLocalization: TextLocalization by inject()
    private val notificationRepository: NotificationRepository by inject()


    fun onDone(diff: ImmoListDiffer.Diff<Type>) {
        diff.newItems.forEach { showNewItemNotification(it) }
        diff.deletedItems.forEach { showDeletedItemNotification(it) }
        diff.modifiedItems.forEach { showModifiedItemNotification(it) }
    }


    /**
     * Item notifications for add/delete/modify
     */

    private fun showNewItemNotification(item: ImmoItem) {
        val title = textLocalization.getString(R.string.notifications_item_new_title)
        val model = item.itemNotification(title)
        notificationRepository.showNotification(model)
    }

    private fun showDeletedItemNotification(item: ImmoItem) {
        val title = textLocalization.getString(R.string.notifications_item_deleted_title)
        val model = item.itemNotification(title)
        notificationRepository.showNotification(model)
    }

    private fun showModifiedItemNotification(item: ImmoItem) {
        val title = textLocalization.getString(R.string.notifications_item_modified_title)
        val model = item.itemNotification(title)
        notificationRepository.showNotification(model)
    }

    private fun ImmoItem.itemNotification(title: String): NotificationModel {

        val text = textLocalization.getString(
            R.string.notifications_item_summary,
            this.title, rooms, livingSpace, warmRent, inserted, lastModified
        )

        return NotificationModel(
            title = title,
            text = text,
            notificationId = this.id.toInt(),
            deepLinkOnClick = urlBuilder.getApartmentUrl(this).toString()
        )
    }

}
