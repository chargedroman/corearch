package com.r.immoscoutpuller.background

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



        return Result.success()
    }

}
