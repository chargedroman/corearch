package com.r.immoscoutpuller.screens.diff

import androidx.lifecycle.MutableLiveData
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.background.ImmoListDiffer
import com.r.immoscoutpuller.immoscout.DIFF_PREFIX
import com.r.immoscoutpuller.model.ImmoItem
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.viewmodel.BaseViewModel
import com.roman.basearch.viewmodel.launch
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 17.07.20
 */

class DifferenceViewModel : BaseViewModel() {

    val diffItems: MutableLiveData<List<ImmoListDiffer.Diff<ImmoItem>>> = MutableLiveData()

    private val localRepository: LocalRepository by inject()
    private val textLocalization: TextLocalization by inject()


    init {
        fetchAllDiffItems()
    }

    fun onUserRefreshedData() {
        fetchAllDiffItems()
    }

    fun onDiffItemClicked(item: ImmoListDiffer.Diff<ImmoItem>) {

    }

    private fun fetchAllDiffItems() {

        val getItemsFlow = localRepository
            .readAllWithPrefix<ImmoListDiffer.Diff<ImmoItem>>(DIFF_PREFIX)

        launch(
            getItemsFlow,
            { diffItems.postValue(it) },
            { handleErrorOnReadDiffs(it) }
        )
    }

    private fun handleErrorOnReadDiffs(error: Throwable) {
        val errorMessage = textLocalization.getString(R.string.diff_error, error.toString())
        message.postValue(errorMessage)
        this.error.postValue(error)
    }

}
