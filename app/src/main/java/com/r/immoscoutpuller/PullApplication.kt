package com.r.immoscoutpuller

import com.r.immoscoutpuller.di.externalModule
import com.r.immoscoutpuller.repository.impl.AnalyticsRepositoryImpl
import com.roman.basearch.repository.AnalyticsRepository
import com.roman.basearch.view.BaseApplication
import net.danlew.android.joda.JodaTimeAndroid
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

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this);
    }

}