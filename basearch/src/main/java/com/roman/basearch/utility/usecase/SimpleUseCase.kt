package com.roman.basearch.utility.usecase

import org.koin.core.KoinComponent

/**
 *
 * Author: romanvysotsky
 * Created: 08.09.21
 */

interface SimpleUseCase<Arg>: KoinComponent {
    fun execute(arg: Arg)
}
