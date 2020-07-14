package com.r.immoscoutpuller.repository

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PullWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        println("started work!")
        Thread.sleep(3000)
        println("stopped work!")
        return Result.success()
    }

}
