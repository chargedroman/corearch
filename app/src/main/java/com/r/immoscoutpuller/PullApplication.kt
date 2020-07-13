package com.r.immoscoutpuller

import android.app.Application
import com.roman.basearch.di.utilityModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */


class PullApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {

        startKoin {
            androidContext(this@PullApplication)
            androidLogger()
            modules(listOf(utilityModule))
        }
    }

}