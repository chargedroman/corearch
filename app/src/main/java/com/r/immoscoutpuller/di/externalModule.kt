package com.r.immoscoutpuller.di

import com.r.immoscoutpuller.immoscout.ImmoScoutParser
import com.r.immoscoutpuller.immoscout.ImmoScoutParserImpl
import com.r.immoscoutpuller.notifications.NotificationRepository
import com.r.immoscoutpuller.notifications.NotificationRepositoryImpl
import com.r.immoscoutpuller.repository.ImmoRepository
import com.r.immoscoutpuller.repository.ImmoUrlRepository
import com.r.immoscoutpuller.repository.WorkRepository
import com.r.immoscoutpuller.repository.impl.ImmoRepositoryImpl
import com.r.immoscoutpuller.repository.impl.ImmoUrlRepositoryImpl
import com.r.immoscoutpuller.repository.impl.WorkRepositoryImpl
import org.koin.dsl.module

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

val externalModule = module {

    single<ImmoRepository> { ImmoRepositoryImpl() }

    single<ImmoScoutParser> { ImmoScoutParserImpl() }

    single<ImmoUrlRepository> { ImmoUrlRepositoryImpl() }

    single<WorkRepository> { WorkRepositoryImpl() }

    single<NotificationRepository> { NotificationRepositoryImpl(get()) }

}
