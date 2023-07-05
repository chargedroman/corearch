package com.roman.basearch.view

import android.app.Application
import com.roman.basearch.di.utilityModule
import com.roman.basearch.repository.AnalyticsRepository
import com.tencent.mmkv.MMKV
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

abstract class BaseApplication : Application() {


    abstract fun createAdditionalDependencyInjectionModules(): List<Module>
    abstract fun createAnalyticsRepository(): AnalyticsRepository


    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        MMKV.initialize(this)
        initKoin()
    }

    private fun initKoin() {

        val allModules = mutableListOf<Module>()
        allModules.addAll(createAdditionalDependencyInjectionModules())
        allModules.add(module { single { createAnalyticsRepository() } })
        allModules.add(utilityModule)

        startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            modules(allModules)
        }
    }

}
