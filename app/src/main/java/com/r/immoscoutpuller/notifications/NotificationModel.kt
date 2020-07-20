package com.r.immoscoutpuller.notifications

import com.r.immoscoutpuller.R

/**
 *
 * Author: romanvysotsky
 * Created: 15.07.20
 */

data class NotificationModel(
    val iconResId: Int = R.drawable.ic_house_solid,
    val title: String = "",
    val text: String = "",
    val showProgress: Boolean = false,
    val isSilent: Boolean = false,
    val notificationId: Int = NOTIFICATION_ID,
    val deepLinkOnClick: String? = null
) {

    companion object {
        const val NOTIFICATION_ID = 42
    }

}
