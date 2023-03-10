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


    override val id = pojo.id.toString()
    override val dataTypeHashCode: Int = pojo.hashCode()

    override val warmRent = warmRent()
    override val rooms = rooms()
    override val livingSpace = livingSpace()

    override val title = pojo.details?.title ?: ""
    override val inserted = inserted()
    override val lastModified = lastModified()

    override fun pojoStringDump(): String {
        return pojo.toString()
    }


    private fun inserted(): String {
        if(pojo.publishDate == null)
            return ""

        val inserted = textLocalization.getDateDifferenceToToday(pojo.publishDate)
        return textLocalization.getString(R.string.item_inserted, inserted)
    }

    private fun lastModified(): String {
        if(pojo.modification == null)
            return ""

        val inserted = textLocalization.getDateDifferenceToToday(pojo.modification)
        return textLocalization.getString(R.string.item_last_modified, inserted)
    }

    private fun warmRent(): String {
        val rent = pojo.details?.calculatedTotalRent?.totalRent
            ?: return ""

        val price = textLocalization.formatDecimal(rent.value ?: 0.0)
        return price+" "+rent.currency
    }

    private fun rooms(): String {
        if(pojo.details?.numberOfRooms == null)
            return ""

        val rooms = textLocalization.formatDecimal(pojo.details.numberOfRooms)
        return textLocalization.getString(R.string.item_rooms, rooms)
    }

    private fun livingSpace(): String {
        if(pojo.details?.livingSpace == null)
            return ""

        val space = textLocalization.formatDecimal(pojo.details.livingSpace)
        return textLocalization.getString(R.string.item_living_space, space)
    }

}
