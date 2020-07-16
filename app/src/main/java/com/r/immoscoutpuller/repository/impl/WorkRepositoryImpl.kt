package com.r.immoscoutpuller.repository.impl

import androidx.lifecycle.LiveData
import androidx.work.*
import com.r.immoscoutpuller.background.worker.ImmoScoutPullWorker
import com.r.immoscoutpuller.background.worker.ImmoWeltPullWorker
import com.r.immoscoutpuller.immoscout.IMMO_SCOUT_ITEMS
import com.r.immoscoutpuller.immoscout.IMMO_WELT_ITEMS
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
    }


    override fun startPullingWork() {

        val workManager = WorkManager.getInstance()
        val work1 = createPullWork(ImmoScoutPullWorker::class.java)
        val work2 = createPullWork(ImmoWeltPullWorker::class.java)

        val policy = ExistingPeriodicWorkPolicy.REPLACE

        workManager.enqueueUniquePeriodicWork(IMMO_SCOUT_ITEMS, policy, work1)
        workManager.enqueueUniquePeriodicWork(IMMO_WELT_ITEMS, policy, work2)

    }

    override fun stopPullingWork() {

        val workManager = WorkManager.getInstance()
        workManager.cancelUniqueWork(IMMO_SCOUT_ITEMS)
        workManager.cancelUniqueWork(IMMO_WELT_ITEMS)

    }

    override fun pullWorkLiveData(): LiveData<List<WorkInfo>> {

        val workManager = WorkManager.getInstance()
        return workManager.getWorkInfosForUniqueWorkLiveData(IMMO_SCOUT_ITEMS)

    }


    private fun <T: ListenableWorker> createPullWork(clazz: Class<T>): PeriodicWorkRequest {
        return PeriodicWorkRequest
            .Builder(clazz, 15, TimeUnit.MINUTES)
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
