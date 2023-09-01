package com.roman.basearch.utility.usecase

import android.app.Application
import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.roman.basearch.utility.TextLocalization
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

/**
 * Schedules at the given time the given notification
 *
 * Author: romanvysotsky
 * Created: 01.09.23
 */

class SchedulePushNotificationUseCase: SimpleUseCase<Pair<Long, ShowPushNotificationUseCase.Arg>> {

    private val application: Application by inject()
    private val textLocalization: TextLocalization by inject()


    override fun execute(arg: Pair<Long, ShowPushNotificationUseCase.Arg>) {
        val currentTime = textLocalization.getCurrentTime().timeInMillis
        val notificationTime = arg.first
        val diff = notificationTime - currentTime

        val manager = WorkManager.getInstance(application)
        val notification = arg.second
        val request = OneTimeWorkRequestBuilder<MyWorker>()
            .addTag(notification.tag)
            .setInitialDelay(diff, TimeUnit.MILLISECONDS)
            .setInputData(wrapArg(notification))
            .build()

        manager
            .beginUniqueWork(notification.id.toString(), ExistingWorkPolicy.REPLACE, request)
            .enqueue()
    }

    private fun wrapArg(arg: ShowPushNotificationUseCase.Arg): Data {
        return workDataOf(
            "id" to arg.id,
            "tag" to arg.tag,
            "icon" to arg.icon,
            "title" to arg.title,
            "body" to arg.body,
            "deepLink" to arg.deepLink
        )
    }



    class MyWorker(context: Context, workerParameters: WorkerParameters)
        : Worker(context, workerParameters), KoinComponent {

        private val showPush: ShowPushNotificationUseCase by inject()

        override fun doWork(): Result {
            val arg = unWrapArg()
            showPush.execute(arg)
            return Result.success()
        }

        private fun unWrapArg(): ShowPushNotificationUseCase.Arg {
            val id = inputData.getLong("id", 0)
            val tag = inputData.getString("tag") ?: ""
            val icon = inputData.getInt("icon", 0)
            val title = inputData.getString("title") ?: ""
            val body = inputData.getString("body") ?: ""
            val deepLink = inputData.getString("deepLink")

            return ShowPushNotificationUseCase.Arg(
                id = id,
                tag = tag,
                icon = icon,
                title = title,
                body = body,
                deepLink = deepLink
            )
        }

    }

}
