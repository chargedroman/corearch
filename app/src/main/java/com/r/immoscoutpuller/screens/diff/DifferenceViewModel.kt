package com.r.immoscoutpuller.screens.diff

import androidx.lifecycle.MutableLiveData
import com.r.immoscoutpuller.background.ImmoListDiffer
import com.r.immoscoutpuller.model.ImmoItem
import com.roman.basearch.viewmodel.BaseViewModel

/**
 *
 * Author: romanvysotsky
 * Created: 17.07.20
 */

class DifferenceViewModel : BaseViewModel() {

    val diffItems: MutableLiveData<List<ImmoListDiffer.Diff<ImmoItem>>> = MutableLiveData()


    init {
        fetchAllDiffItems()
    }

    fun onUserRefreshedData() {
        fetchAllDiffItems()
    }

    fun onDiffItemClicked(item: ImmoListDiffer.Diff<ImmoItem>) {

    }

    private fun fetchAllDiffItems() {

    }

}
