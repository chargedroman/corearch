package com.roman.basearch.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

fun <T> Flow<T>.launchIn(viewModel: BaseViewModel): Job {
    return this
        .onStart { viewModel.isLoading.postValue(true) }
        .onCompletion { viewModel.isLoading.postValue(false) }
        .launchIn(viewModel.viewModelScope)
}

fun <T> BaseViewModel.launch(
    flow: Flow<T>,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Job {

    return flow.flowOn(Dispatchers.IO)
        .onStart { isLoading.postValue(true) }
        .onCompletion { isLoading.postValue(false) }
        .onEach { onSuccess(it) }
        .catch { onError(it) }
        .launchIn(viewModelScope)
}

suspend fun <T> CoroutineScope.launch(flow: Flow<T>): Pair<T?, Throwable?> {

    var result: Pair<T?, Throwable?> = Pair(null, null)

    val job = flow
        .onEach { result = Pair<T?, Throwable?>(it, null) }
        .catch { result = Pair<T?, Throwable?>(null, it) }
        .launchIn(this)

    job.join()

    return result
}

inline fun <reified T> getKoinInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}
