package com.r.immoscoutpuller.repository

import android.util.Log
import com.roman.basearch.repository.AnalyticsRepository

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class AnalyticsRepositoryImpl : AnalyticsRepository {

    override fun logException(error: Throwable) {
        Log.d("ERR-1", error.toString())
    }

}
