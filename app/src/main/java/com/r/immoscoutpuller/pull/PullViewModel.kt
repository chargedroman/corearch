package com.r.immoscoutpuller.pull

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.ImmoScoutRepository
import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
import com.roman.basearch.utility.LocalRepository
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
    private val localRepository: LocalRepository by inject()


    init {
        getApartments()
    }


    fun onUserRefreshedData() {
        getApartments()
    }

    fun getApartments() {

        val request = localRepository.getApartmentsRequest()
        val flow = immoScoutRepository.getMainzApartmentsWeb(request)

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

    private fun LocalRepository.getApartmentsRequest(): RentingApartmentsRequest {

        return RentingApartmentsRequest(
            retrieve("minPrice")?.toDoubleOrNull() ?: 0.0,
            retrieve("maxPrice")?.toDoubleOrNull() ?: 0.0,
            retrieve("minSpace")?.toDoubleOrNull() ?: 0.0,
            retrieve("maxSpace")?.toDoubleOrNull() ?: 0.0,
            retrieve("minRooms")?.toDoubleOrNull() ?: 0.0,
            retrieve("maxRooms")?.toDoubleOrNull() ?: 0.0,
            retrieve("geoCodes") ?: ""
        )
    }

}
