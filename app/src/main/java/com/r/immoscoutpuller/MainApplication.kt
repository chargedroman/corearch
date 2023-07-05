package com.r.immoscoutpuller

import com.roman.basearch.repository.AnalyticsRepository
import com.roman.basearch.view.BaseApplication
import org.koin.core.module.Module

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */


class MainApplication : BaseApplication() {

    override fun createAdditionalDependencyInjectionModules(): List<Module> {
        return listOf()
    }

    override fun createAnalyticsRepository(): AnalyticsRepository {
        return object : AnalyticsRepository {
            override fun logException(error: Throwable) {
                println(error.stackTrace)
            }
        }
    }

}
