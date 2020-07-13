package com.r.immoscoutpuller.pull

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.ImmoScoutRepository
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
import com.roman.basearch.utility.TextLocalization
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
    private val textLocalization: TextLocalization by inject()


    init {
        onGetApartmentsClicked()
    }


    fun onGetApartmentsClicked() {

        val flow = immoScoutRepository
            .getMainzApartmentsWeb("750", "45", "2")

        launch(
            flow,
            { immoItems.postValue(it) },
            { handleErrorOnGetApartments(it) }
        )
    }


    fun onImmoItemClicked(item: PresentableImmoItem) {
        message.postValue(item.toString())
    }


    fun handleErrorOnGetApartments(error: Throwable) {
        this.error.postValue(error)

        val errorMessage = if(error is JsonSyntaxException) {
            textLocalization.getString(R.string.pull_get_error_parse, error.toString())
        } else {
            textLocalization.getString(R.string.pull_get_error_default, error.toString())
        }

        message.postValue(errorMessage)
    }

}
