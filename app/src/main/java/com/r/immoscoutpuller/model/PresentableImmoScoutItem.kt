package com.r.immoscoutpuller.model

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.viewmodel.getKoinInstance

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PresentableImmoScoutItem(val pojo: ImmoItemResponse): ImmoItem() {

    @Transient private val textLocalization: TextLocalization = getKoinInstance()


    override val id = pojo.id

    override val warmRent = warmRent()
    override val rooms = rooms()
    override val livingSpace = livingSpace()

    override val title = pojo.details.title
    override val inserted = inserted()
    override val lastModified = lastModified()


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

}
