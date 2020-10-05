package com.r.immoscoutpuller.screens.basepull

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.model.ImmoItem
import com.r.immoscoutpuller.repository.ImmoRepository
import com.r.immoscoutpuller.repository.ImmoUrlRepository
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.viewmodel.BaseViewModel
import com.roman.basearch.viewmodel.launch
import kotlinx.coroutines.flow.Flow
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created:
 */

abstract class PullViewModel<Type: ImmoItem> : BaseViewModel() {

    val immoItems: MutableLiveData<List<Type>> = MutableLiveData()
    val immoItemsTotal: MutableLiveData<String> = MutableLiveData()

    val immoRepository: ImmoRepository by inject()
    val textLocalization: TextLocalization by inject()
    val immoUrlBuilder: ImmoUrlRepository by inject()


    abstract fun getItems(): Flow<List<Type>>
    abstract fun getFreshItems(): Flow<List<Type>>


    init {
        getItemsFromCacheOrFresh()
    }


    fun getItemsFromCacheOrFresh() {
        launch(
            getItems(),
            { onImmoItemsReceived(it) },
            { handleErrorOnGetApartments(it) }
        )
    }

    fun onUserRefreshedData() {
        launch(
            getFreshItems(),
            { onImmoItemsReceived(it) },
            { handleErrorOnGetApartments(it) }
        )
    }



    fun onImmoItemClicked(item: Type) {
        val url = immoUrlBuilder.getApartmentUrl(item)
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


    private fun onImmoItemsReceived(items: List<Type>) {
        val size = items.size.toString()
        val total = textLocalization.getString(R.string.pull_title, size)

        immoItems.postValue(items)
        immoItemsTotal.postValue(total)
    }

}
