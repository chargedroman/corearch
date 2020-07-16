package com.r.immoscoutpuller.repository.impl

import androidx.lifecycle.LiveData
import androidx.work.*
import com.r.immoscoutpuller.background.worker.ImmoScoutPullWorker
import com.r.immoscoutpuller.repository.WorkRepository
import java.util.concurrent.TimeUnit

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class WorkRepositoryImpl: WorkRepository {

    companion object {
        const val BACKOFF_DELAY_SECONDS = 60L
        const val WORK_NAME = "PullImmoScoutApartments"
    }


    override fun startPullingWork() {

        val workManager = WorkManager.getInstance()
        val work = createPullWork()
        val policy = ExistingPeriodicWorkPolicy.REPLACE
        workManager.enqueueUniquePeriodicWork(WORK_NAME, policy, work)

    }

    override fun stopPullingWork() {

        val workManager = WorkManager.getInstance()
        workManager.cancelUniqueWork(WORK_NAME)

    }

    override fun pullWorkLiveData(): LiveData<List<WorkInfo>> {

        val workManager = WorkManager.getInstance()
        return workManager.getWorkInfosForUniqueWorkLiveData(WORK_NAME)

    }


    private fun createPullWork(): PeriodicWorkRequest {
        return PeriodicWorkRequest
            .Builder(ImmoScoutPullWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(createConstraints())
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL,
                BACKOFF_DELAY_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    private fun createConstraints(): Constraints {
        return Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
    }

}
