package com.r.immoscoutpuller.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class WorkRepositoryImpl(private val context: Context): WorkRepository {

    companion object {
        const val BACKOFF_DELAY_SECONDS = 60L
        const val WORK_NAME = "PullRentAbleApartments"
    }


    override fun startPullingWork() {

        val workManager = WorkManager.getInstance(context)
        val work = createPullWork()
        val policy = ExistingPeriodicWorkPolicy.REPLACE
        workManager.enqueueUniquePeriodicWork(WORK_NAME, policy, work)

    }

    override fun stopPullingWork() {

        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(WORK_NAME)

    }

    override fun pullWorkLiveData(): LiveData<List<WorkInfo>> {

        val workManager = WorkManager.getInstance(context)
        return workManager.getWorkInfosForUniqueWorkLiveData(WORK_NAME)

    }


    private fun createPullWork(): PeriodicWorkRequest {
        return PeriodicWorkRequest
            .Builder(PullWorker::class.java, 1, TimeUnit.MINUTES)
            .setConstraints(createConstraints())
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, BACKOFF_DELAY_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    private fun createConstraints(): Constraints {
        return Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
    }

}