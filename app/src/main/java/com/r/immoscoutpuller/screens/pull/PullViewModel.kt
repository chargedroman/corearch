package com.r.immoscoutpuller.screens.pull

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.repository.ImmoRepository
import com.r.immoscoutpuller.repository.ImmoUrlRepository
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

    val immoItems: MutableLiveData<List<PresentableImmoScoutItem>> = MutableLiveData()

    private val immoScoutRepository: ImmoRepository by inject()
    private val textLocalization: TextLocalization by inject()
    private val immoUrlBuilder: ImmoUrlRepository by inject()


    init {
        getApartments()
    }


    fun onUserRefreshedData() {
        getApartments()
    }

    fun getApartments() {

        val flow =
            immoScoutRepository.getImmoScoutApartmentsWeb()

        launch(
            flow,
            { immoItems.postValue(it) },
            { handleErrorOnGetApartments(it) }
        )
    }


    fun onImmoItemClicked(item: PresentableImmoScoutItem) {
        val url = immoUrlBuilder.getImmoScoutApartmentUrl(item.pojo)
        val navigation = PullNavigation.ToWeb(url.toString()) {
            this.error.postValue(it)
            this.message.postValue(textLocalization.getString(R.string.item_could_not_open))
        }
        this.navigation.postValue(navigation)
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
