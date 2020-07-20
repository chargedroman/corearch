package com.r.immoscoutpuller.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.r.immoscoutpuller.MainActivity
import com.r.immoscoutpuller.R

/**
 *
 * Author: romanvysotsky
 * Created: 15.07.20
 */

class NotificationRepositoryImpl(private val context: Context):
    NotificationRepository {

    companion object {
        const val BACKGROUND_WORK_NOTIFICATIONS_CHANNEL_ID = "ImmoPullerBackgroundNotifications"
    }

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val notificationChannel =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            getBackgroundWorkNotificationChannel()
        else
            null


    override fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    override fun showNotification(model: NotificationModel) {

        if (notificationChannel != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = getNotification(model)
        notificationManager.notify(model.notificationId, notification)
    }


    private fun getNotification(model: NotificationModel): Notification {

        return NotificationCompat.Builder(context, BACKGROUND_WORK_NOTIFICATIONS_CHANNEL_ID)
            .setSmallIcon(model.iconResId)
            .setContentTitle(model.title)
            .setContentText(model.text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(model.text))
            .setContentIntent(getIntentFrom(model) ?: getAppIntent())
            .setSilent(model)
            .setProgress(model)
            .setAutoCancel(true)
            .build()
    }


    private fun NotificationCompat.Builder.setSilent(model: NotificationModel)
            : NotificationCompat.Builder {

        return if(model.isSilent) {
            this.setVibrate(longArrayOf(0))
        } else {
            this
        }
    }


    private fun NotificationCompat.Builder.setProgress(model: NotificationModel)
            : NotificationCompat.Builder {

        return if(model.showProgress) {
            this.setProgress(0,0,true)
        } else {
            this
        }
    }

    private fun getIntentFrom(model: NotificationModel): PendingIntent? {
        val deepLinkUri = model.deepLinkOnClick ?: return null
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkUri))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun getAppIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun getBackgroundWorkNotificationChannel(): NotificationChannel {

        val channelName = context.getString(R.string.notifications_channel_name)
        val channelDesc = context.getString(R.string.notifications_channel_desc)

        val backgroundWorkChannel: NotificationChannel
        backgroundWorkChannel = NotificationChannel(
            BACKGROUND_WORK_NOTIFICATIONS_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        backgroundWorkChannel.description = channelDesc
        backgroundWorkChannel.lightColor = Color.GREEN
        backgroundWorkChannel.enableLights(false)
        backgroundWorkChannel.enableVibration(false)

        return backgroundWorkChannel
    }

}
