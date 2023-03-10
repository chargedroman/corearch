package com.roman.basearch.di

import com.roman.basearch.repository.OkHttpClientFactory
import com.roman.basearch.repository.PermissionRepository
import com.roman.basearch.repository.PermissionRepositoryImpl
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.utility.LocalRepositoryImpl
import com.roman.basearch.utility.TextLocalization
import org.koin.dsl.module

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

val utilityModule = module {

    single<PermissionRepository> { PermissionRepositoryImpl() }

    single { TextLocalization.createInstance(get()) }

    single<LocalRepository> { LocalRepositoryImpl(get()) }

    single { OkHttpClientFactory().createStandardInstance() }

}
