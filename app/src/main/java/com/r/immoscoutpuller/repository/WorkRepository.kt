package com.r.immoscoutpuller.repository

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

interface WorkRepository {

    fun startPullingWork()
    fun stopPullingWork()
    fun pullWorkLiveData(): LiveData<List<WorkInfo>>

}
