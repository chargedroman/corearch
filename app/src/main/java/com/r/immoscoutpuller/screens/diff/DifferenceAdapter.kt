package com.r.immoscoutpuller.screens.diff

import androidx.recyclerview.widget.DiffUtil
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.background.ImmoListDiffer
import com.r.immoscoutpuller.databinding.ItemDiffBinding
import com.r.immoscoutpuller.model.ImmoItem
import com.roman.basearch.view.list.BaseItemViewModel
import com.roman.basearch.view.list.BaseListAdapter

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class DifferenceAdapter(clickFunction: (ImmoListDiffer.Diff<ImmoItem>) -> Unit) :
    BaseListAdapter<ImmoListDiffer.Diff<ImmoItem>, DiffDiffUtil, ItemDiffBinding>(
        clickFunction,
        DiffDiffUtil()
    ) {

    override val layoutResourceId: Int get() = R.layout.item_diff
    override val viewModelFactory: () -> BaseItemViewModel<ImmoListDiffer.Diff<ImmoItem>> =
        { DiffItemViewModel() }

}

class DiffDiffUtil : DiffUtil.ItemCallback<ImmoListDiffer.Diff<ImmoItem>>() {
    override fun areContentsTheSame(p0: ImmoListDiffer.Diff<ImmoItem>, p1: ImmoListDiffer.Diff<ImmoItem>)
            : Boolean {
        return p0 == p1
    }

    override fun areItemsTheSame(p0: ImmoListDiffer.Diff<ImmoItem>, p1: ImmoListDiffer.Diff<ImmoItem>)
            : Boolean {
        return p0.creationDate == p1.creationDate
    }
}
