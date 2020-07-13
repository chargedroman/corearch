package com.r.immoscoutpuller.pull

import androidx.lifecycle.MutableLiveData
import com.r.immoscoutpuller.immoscout.ImmoScoutRepository
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
import com.roman.basearch.viewmodel.BaseViewModel
import com.roman.basearch.viewmodel.launch
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created:
 */

class PullViewModel : BaseViewModel() {

    val immoItems: MutableLiveData<List<PresentableImmoItem>> = MutableLiveData()

    private val immoScoutRepository: ImmoScoutRepository by inject()


    init {
        onGetApartmentsClicked()
    }


    fun onGetApartmentsClicked() {

        val flow = immoScoutRepository
            .getMainzApartmentsWeb("750", "45", "2")

        launch(
            flow,
            { immoItems.postValue(it) },
            { error.postValue(it) }
        )
    }


    fun onImmoItemClicked(item: PresentableImmoItem) {
        message.postValue(item.toString())
    }

}
