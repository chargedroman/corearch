package com.r.immoscoutpuller.pull

import androidx.recyclerview.widget.DiffUtil
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.ItemImmoBinding
import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem
import com.roman.basearch.view.list.BaseItemViewModel
import com.roman.basearch.view.list.BaseListAdapter

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PullAdapter(clickFunction: (PresentableImmoItem) -> Unit) :
    BaseListAdapter<PresentableImmoItem, ImmoItemDiffUtil, ItemImmoBinding>(
        clickFunction,
        ImmoItemDiffUtil
    ) {

    override val layoutResourceId: Int get() = R.layout.item_immo
    override val viewModelFactory: () -> BaseItemViewModel<PresentableImmoItem> =
        { PresentableImmoItem.ViewModel() }

}

object ImmoItemDiffUtil : DiffUtil.ItemCallback<PresentableImmoItem>() {
    override fun areContentsTheSame(p0: PresentableImmoItem, p1: PresentableImmoItem): Boolean {
        return p0.pojo == p1.pojo
    }

    override fun areItemsTheSame(p0: PresentableImmoItem, p1: PresentableImmoItem): Boolean {
        return p0.pojo.id == p1.pojo.id
    }
}
