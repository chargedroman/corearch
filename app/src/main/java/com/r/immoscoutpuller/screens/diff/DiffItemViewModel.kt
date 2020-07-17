package com.r.immoscoutpuller.screens.diff

import androidx.databinding.ObservableField
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.background.ImmoListDiffer
import com.r.immoscoutpuller.model.ImmoItem
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.view.list.BaseItemViewModel
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 17.07.20
 */

class DiffItemViewModel : BaseItemViewModel<ImmoListDiffer.Diff<ImmoItem>>() {

    val textLocalization: TextLocalization by inject()

    val item: ObservableField<ImmoListDiffer.Diff<ImmoItem>> = ObservableField()
    val creationDate: ObservableField<String> = ObservableField()


    override fun bindItem(item: ImmoListDiffer.Diff<ImmoItem>) {
        this.item.set(item)

        val date = textLocalization.getSimpleDate(item.creationDate)
        val text = textLocalization.getString(R.string.diff_created, date)
        this.creationDate.set(text)
    }

}
