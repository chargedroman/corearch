package com.roman.basearch.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

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
