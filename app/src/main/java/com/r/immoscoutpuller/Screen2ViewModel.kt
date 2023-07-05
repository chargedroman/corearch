package com.r.immoscoutpuller

import androidx.lifecycle.MutableLiveData
import com.roman.basearch.viewmodel.BaseViewModel
import com.roman.basearch.viewmodel.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

class Screen2ViewModel: BaseViewModel() {

    val list = MutableLiveData<List<ListItem>>()


    init {
        refreshItems()
    }


    fun refreshItems() {
        //would call some repository usually

        launch(
            getDummyItems(),
            { list.postValue(it) },
            { message.postValue("oops!") }
        )
    }



    private fun getDummyItems(): Flow<List<ListItem>> = flow {
        val items = listOf(ListItem(), ListItem(), ListItem(), ListItem(), ListItem())
        this.emit(items)
    }



    fun onListItemClicked(item: ListItem) {
        message.postValue(item.name)
    }

}
