package com.r.immoscoutpuller.di

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.*
import com.r.immoscoutpuller.repository.AnalyticsRepositoryImpl
import com.roman.basearch.repository.AnalyticsRepository
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

    single<AnalyticsRepository> { AnalyticsRepositoryImpl() }

    single<ImmoScoutParser> { ImmoScoutParserImpl() }

    single<ImmoScoutUrlBuilder> { ImmoScoutUrlBuilderImpl() }

}
