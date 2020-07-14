package com.r.immoscoutpuller.di

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.*
import com.r.immoscoutpuller.repository.WorkRepository
import com.r.immoscoutpuller.repository.WorkRepositoryImpl
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

    single<ImmoScoutRepository> { ImmoScoutRepositoryImpl() }

    single<ImmoScoutParser> { ImmoScoutParserImpl() }

    single<ImmoScoutUrlBuilder> { ImmoScoutUrlBuilderImpl() }

    single<WorkRepository> { WorkRepositoryImpl(get()) }

}
