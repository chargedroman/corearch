package com.r.immoscoutpuller.notifications

/**
 *
 * Author: romanvysotsky
 * Created: 15.07.20
 */

interface NotificationRepository {

    fun showNotification(model: NotificationModel)
    fun cancelNotification(notificationId: Int)

}
