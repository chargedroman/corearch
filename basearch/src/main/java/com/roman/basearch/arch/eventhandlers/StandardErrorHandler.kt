package com.roman.basearch.arch.eventhandlers

import androidx.lifecycle.Observer
import com.roman.basearch.repository.AnalyticsRepository
import com.roman.basearch.view.BaseActivity
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

class StandardErrorHandler(private val context: BaseActivity<*, *>)
    : Observer<Throwable>, KoinComponent {

    private val analytics: AnalyticsRepository by inject()


    override fun onChanged(value: Throwable) {
        log(value)
    }

    private fun log(error: Throwable) {
        analytics.logException(error)
    }

}
