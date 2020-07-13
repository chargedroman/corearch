package com.roman.basearch.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

@ExperimentalCoroutinesApi
fun <T> Flow<T>.launchIn(viewModel: BaseViewModel): Job {
    return this
        .onStart { viewModel.isLoading.postValue(true) }
        .onCompletion { viewModel.isLoading.postValue(false) }
        .launchIn(viewModel.viewModelScope)
}
