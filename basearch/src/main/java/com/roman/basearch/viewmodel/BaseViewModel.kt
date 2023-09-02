package com.roman.basearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andcharge.mobile.base.architecture.components.SingleLiveEvent
import com.roman.basearch.R
import com.roman.basearch.models.ActionMessage
import com.roman.basearch.navigation.Navigation
import com.roman.basearch.utility.TextLocalization
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

abstract class BaseViewModel: ViewModel(), KoinComponent {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val closeKeyBoard: SingleLiveEvent<Unit> = SingleLiveEvent()
    val message: SingleLiveEvent<String> = SingleLiveEvent()
    val error: SingleLiveEvent<Throwable> = SingleLiveEvent()
    val actionMessage: SingleLiveEvent<ActionMessage> = SingleLiveEvent()
    val navigation: SingleLiveEvent<Navigation> = SingleLiveEvent()

    val localization: TextLocalization by inject()


    fun launchWithOnError(onError: ((Throwable) -> Unit)? = null, block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                isLoading.postValue(true)
                block()
            } catch (e: Throwable) {
                onError?.invoke(e) ?: onErrorDefault(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun onErrorDefault(e: Throwable) {
        val errorMessage = localization.getString(R.string.common_error_default)
        error.postValue(e)
        message.postValue(errorMessage)
    }

}
