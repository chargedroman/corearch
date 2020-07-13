package com.roman.basearch.di

import com.roman.basearch.utility.TextLocalization
import org.koin.dsl.module

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

val utilityModule = module {

    single {
        TextLocalization.createInstance(
            get()
        )
    }

}
