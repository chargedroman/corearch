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
        const val ID_MAIN = "ImmoPullerBackgroundNotifications1"
        const val ID_SECOND = "ImmoPullerBackgroundNotifications2"
    }

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    private val mainChannel =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            getBackgroundWorkNotificationChannel(ID_MAIN, NotificationManager.IMPORTANCE_HIGH)
        else
            null

    private val secondChannel =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            getBackgroundWorkNotificationChannel(ID_SECOND, NotificationManager.IMPORTANCE_LOW)
        else
            null


    override fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    override fun showNotification(model: NotificationModel) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(mainChannel != null) notificationManager.createNotificationChannel(mainChannel)
            if(secondChannel != null) notificationManager.createNotificationChannel(secondChannel)
        }

        val notification = getNotification(model)
        notificationManager.notify(model.notificationId, notification)
    }


    private fun getNotification(model: NotificationModel): Notification {

        val channelId = model.getChannelId()

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(model.iconResId)
            .setContentTitle(model.title)
            .setContentText(model.text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(model.text))
            .setContentIntent(getIntentFrom(model) ?: getAppIntent())
            .setProgress(model)
            .setAutoCancel(true)
            .build()
    }


    private fun NotificationModel.getChannelId(): String {

        return if(isSilent) {
            ID_SECOND
        } else {
            ID_MAIN
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
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun getAppIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun getBackgroundWorkNotificationChannel(
        channelId: String,
        importance: Int
    ): NotificationChannel {

        val channelName = context.getString(R.string.notifications_channel_name)
        val channelDesc = context.getString(R.string.notifications_channel_desc)
        val backgroundWorkChannel = NotificationChannel(channelId, channelName, importance)

        backgroundWorkChannel.description = channelDesc
        backgroundWorkChannel.lightColor = Color.GREEN
        backgroundWorkChannel.enableLights(false)
        backgroundWorkChannel.enableVibration(false)

        return backgroundWorkChannel
    }

}
