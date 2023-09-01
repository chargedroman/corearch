package com.roman.basearch.utility.usecase

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.roman.basearch.R
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 01.09.23
 */

class ShowPushNotificationUseCase: SimpleUseCase<ShowPushNotificationUseCase.Arg> {


    companion object {
        const val ADMIN_CHANNEL_ID = "BroPushNotifications"

        fun getAdminChannel(context: Context): NotificationChannel {
            val adminChannelName = context.getString(R.string.common_notification_channel_name)
            val adminChannelDescription = context.getString(R.string.common_notification_channel_description)
            val adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH)

            adminChannel.description = adminChannelDescription
            adminChannel.enableLights(true)
            adminChannel.lightColor = Color.RED
            adminChannel.enableVibration(true)
            return adminChannel
        }
    }


    private val application: Application by inject()


    override fun execute(arg: Arg) {
        val notificationManager: NotificationManager =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !notificationManager.areNotificationsEnabled())
            return

        notificationManager.createNotificationChannel(getAdminChannel(application))

        val notificationBuilder = NotificationCompat.Builder(application, ADMIN_CHANNEL_ID)
            .setSmallIcon(arg.icon)
            .setContentTitle(arg.title)
            .setContentText(arg.body)
            .setContentIntent(getPendingIntent(arg))
            .setAutoCancel(true)

        val notification = notificationBuilder.build()
        notificationManager.notify(arg.hashCode(), notification)
    }


    private fun getPendingIntent(arg: Arg): PendingIntent {
        val intent = Intent(application, getMainLauncherActivityClass()).apply {
            if (arg.deepLink.isNullOrEmpty().not())
                this.data = Uri.parse(arg.deepLink)
        }
        return PendingIntent.getActivity(application, 1, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun getMainLauncherActivityClass(): Class<*> {
        val pm = application.packageManager
        val launchIntent = Intent(Intent.ACTION_MAIN)
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val resolveInfo = pm.resolveActivity(launchIntent, 0)
        val mainActivityClassName = resolveInfo?.activityInfo?.name
        return Class.forName(mainActivityClassName ?: "")
    }



    data class Arg(
        val id: Long,
        val tag: String,
        val icon: Int,
        val title: String,
        val body: String,
        val deepLink: String?,
    )


}
