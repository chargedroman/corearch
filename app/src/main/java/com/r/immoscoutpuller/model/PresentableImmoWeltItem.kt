package com.r.immoscoutpuller.model

import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immowelt.model.ImmoWeltItemResponse
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.viewmodel.getKoinInstance

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class PresentableImmoWeltItem(val pojo: ImmoWeltItemResponse): ImmoItem() {

    @Transient
    private val textLocalization: TextLocalization = getKoinInstance()

    override val id = pojo.id.toString()
    override val dataTypeHashCode: Int = pojo.hashCode()

    override val title = pojo.title
    override val warmRent = pojo.price+" EUR +"
    override val rooms = textLocalization.getString(R.string.item_rooms, pojo.rooms)
    override val livingSpace = textLocalization.getString(R.string.item_living_space, pojo.livingSpace)

    override fun pojoStringDump(): String {
        return pojo.toString()
    }


    override val inserted =
        textLocalization.getString(
            R.string.item_inserted,
            textLocalization.getString(R.string.common_unknown)
        )

    override val lastModified =
        textLocalization.getString(
            R.string.item_last_modified,
            textLocalization.getString(R.string.common_unknown)
        )

}
