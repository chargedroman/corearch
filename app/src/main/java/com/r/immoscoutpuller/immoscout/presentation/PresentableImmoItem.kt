package com.r.immoscoutpuller.immoscout.presentation

import androidx.databinding.ObservableField
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.view.list.BaseItemViewModel
import com.roman.basearch.viewmodel.getKoinInstance
import org.koin.core.KoinComponent
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PresentableImmoItem(val pojo: ImmoItemResponse): KoinComponent, Serializable {

    @Transient private val textLocalization: TextLocalization = getKoinInstance()


    val warmRent = warmRent()
    val rooms = rooms()
    val livingSpace = livingSpace()

    val title = pojo.details.title
    val inserted = inserted()
    val lastModified = lastModified()


    private fun inserted(): String {
        val inserted = textLocalization.getDateDifferenceToToday(pojo.publishDate)
        return textLocalization.getString(R.string.item_inserted, inserted)
    }

    private fun lastModified(): String {
        val inserted = textLocalization.getDateDifferenceToToday(pojo.modification)
        return textLocalization.getString(R.string.item_last_modified, inserted)
    }

    private fun warmRent(): String {
        val price = textLocalization.formatDecimal(pojo.details.calculatedPrice.value)
        return price+" "+pojo.details.calculatedPrice.currency
    }

    private fun rooms(): String {
        val rooms = textLocalization.formatDecimal(pojo.details.numberOfRooms)
        return textLocalization.getString(R.string.item_rooms, rooms)
    }

    private fun livingSpace(): String {
        val space = textLocalization.formatDecimal(pojo.details.livingSpace)
        return textLocalization.getString(R.string.item_living_space, space)
    }


    class ViewModel: BaseItemViewModel<PresentableImmoItem>() {

        val item: ObservableField<PresentableImmoItem> = ObservableField()

        override fun bindItem(item: PresentableImmoItem) {
            this.item.set(item)
        }

    }

}
