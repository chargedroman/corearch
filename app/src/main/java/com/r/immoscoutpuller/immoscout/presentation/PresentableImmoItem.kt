package com.r.immoscoutpuller.immoscout.presentation

import androidx.databinding.ObservableField
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.view.list.BaseItemViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PresentableImmoItem(val pojo: ImmoItemResponse): KoinComponent {

    private val textLocalization: TextLocalization by inject()


    val warmRent = warmRent()
    val rooms = rooms()
    val title = pojo.details.title


    private fun warmRent(): String {
        return pojo.details.calculatedPrice.value.toString()+pojo.details.calculatedPrice.currency
    }

    private fun rooms(): String {
        val rooms = pojo.details.numberOfRooms.toString()
        return textLocalization.getString(R.string.item_rooms, rooms)
    }


    class ViewModel: BaseItemViewModel<PresentableImmoItem>() {

        val item: ObservableField<PresentableImmoItem> = ObservableField()

        override fun bindItem(item: PresentableImmoItem) {
            this.item.set(item)
        }

    }

}
