package com.r.immoscoutpuller.pull

import androidx.lifecycle.MutableLiveData
import com.r.immoscoutpuller.immoscout.ImmoScoutRepository
import com.roman.basearch.viewmodel.BaseViewModel
import com.roman.basearch.viewmodel.launch
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created:
 */

class PullViewModel : BaseViewModel() {

    val result: MutableLiveData<String> = MutableLiveData()

    val immoScoutRepository: ImmoScoutRepository by inject()


    init {
        onGetApartmentsClicked()
    }


    fun onGetApartmentsClicked() {

        val flow = immoScoutRepository
            .getMainzApartments("750", "45", "2")

        launch(
            flow,
            { result.postValue(it.toString()); message.postValue(it.toString()) },
            { error.postValue(it) }
        )
    }

}
