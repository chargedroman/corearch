package com.r.immoscoutpuller

import com.r.immoscoutpuller.di.externalModule
import com.r.immoscoutpuller.repository.AnalyticsRepositoryImpl
import com.roman.basearch.repository.AnalyticsRepository
import com.roman.basearch.view.BaseApplication
import org.koin.core.module.Module

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */


class PullApplication : BaseApplication() {

    override fun createAdditionalDependencyInjectionModules(): List<Module> {
        return listOf(externalModule)
    }

    override fun createAnalyticsRepository(): AnalyticsRepository {
        return AnalyticsRepositoryImpl()
    }

}