package com.roman.basearch.baseextensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 *
 * Author: romanvysotsky
 * Created: 05.10.20
 */

fun <T> Flow<T>.onErrorDefault(default: T): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    } catch (e: Throwable) {
        emit(default)
    }
}

fun <T> Flow<T>.onErrorDo(alternativeFlow: Flow<T>): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    } catch (e: Throwable) {
        alternativeFlow.collect { value -> emit(value) }
    }
}
