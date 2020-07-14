package com.r.immoscoutpuller.immoscout.presentation

import androidx.databinding.ObservableField
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.r.immoscoutpuller.util.NumberFormatter
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
    val livingSpace = livingSpace()
    val title = pojo.details.title


    private fun warmRent(): String {
        val price = NumberFormatter.formatDecimal(pojo.details.calculatedPrice.value)
        return price+" "+pojo.details.calculatedPrice.currency
    }

    private fun rooms(): String {
        val rooms = NumberFormatter.formatDecimal(pojo.details.numberOfRooms)
        return textLocalization.getString(R.string.item_rooms, rooms)
    }

    private fun livingSpace(): String {
        val space = NumberFormatter.formatDecimal(pojo.details.livingSpace)
        return textLocalization.getString(R.string.item_living_space, space)
    }


    class ViewModel: BaseItemViewModel<PresentableImmoItem>() {

        val item: ObservableField<PresentableImmoItem> = ObservableField()

        override fun bindItem(item: PresentableImmoItem) {
            this.item.set(item)
        }

    }

}
