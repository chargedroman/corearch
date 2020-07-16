package com.r.immoscoutpuller.model

import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.viewmodel.getKoinInstance

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class PresentableImmoWeltItem: ImmoItem() {

    @Transient
    private val textLocalization: TextLocalization = getKoinInstance()

    override val id = ""
    override val dataTypeHashCode: Int = 1

    override val warmRent = ""
    override val rooms = ""
    override val livingSpace = ""

    override val title = ""
    override val inserted = ""
    override val lastModified = ""

}
