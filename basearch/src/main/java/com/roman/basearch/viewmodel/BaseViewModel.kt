package com.roman.basearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andcharge.mobile.base.architecture.components.SingleLiveEvent
import com.roman.basearch.models.ActionMessage
import com.roman.basearch.navigation.Navigation
import org.koin.core.KoinComponent

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

}
