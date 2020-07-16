package com.r.immoscoutpuller.di

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.ImmoScoutParser
import com.r.immoscoutpuller.immoscout.ImmoScoutParserImpl
import com.r.immoscoutpuller.notifications.NotificationRepository
import com.r.immoscoutpuller.notifications.NotificationRepositoryImpl
import com.r.immoscoutpuller.repository.ImmoRepository
import com.r.immoscoutpuller.repository.ImmoScoutWebRepository
import com.r.immoscoutpuller.repository.ImmoUrlRepository
import com.r.immoscoutpuller.repository.WorkRepository
import com.r.immoscoutpuller.repository.impl.ImmoRepositoryImpl
import com.r.immoscoutpuller.repository.impl.ImmoUrlRepositoryImpl
import com.r.immoscoutpuller.repository.impl.WorkRepositoryImpl
import com.roman.basearch.repository.WebRepository
import com.roman.basearch.repository.WebRepositoryFactory
import org.koin.dsl.module

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

val externalModule = module {

    single {
        WebRepositoryFactory()
            .create(
                WebRepository(
                    get(),
                    R.string.immo_base_url,
                    ImmoScoutWebRepository::class.java)
            )
    }

    single<ImmoRepository> { ImmoRepositoryImpl() }

    single<ImmoScoutParser> { ImmoScoutParserImpl() }

    single<ImmoUrlRepository> { ImmoUrlRepositoryImpl() }

    single<WorkRepository> {
        WorkRepositoryImpl(
            get()
        )
    }

    single<NotificationRepository> {
        NotificationRepositoryImpl(
            get()
        )
    }

}
