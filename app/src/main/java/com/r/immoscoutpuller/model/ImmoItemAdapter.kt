package com.r.immoscoutpuller.model

import androidx.recyclerview.widget.DiffUtil
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.ItemImmoBinding
import com.roman.basearch.view.list.BaseItemViewModel
import com.roman.basearch.view.list.BaseListAdapter

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PullAdapter<Type: ImmoItem>(clickFunction: (Type) -> Unit) :
    BaseListAdapter<Type, ImmoItemDiffUtil<Type>, ItemImmoBinding>(
        clickFunction,
        ImmoItemDiffUtil()
    ) {

    override val layoutResourceId: Int get() = R.layout.item_immo
    override val viewModelFactory: () -> BaseItemViewModel<Type> =
        { ImmoItem.ViewModel() }

}

class ImmoItemDiffUtil<Type: ImmoItem> : DiffUtil.ItemCallback<Type>() {
    override fun areContentsTheSame(p0: Type, p1: Type): Boolean {
        return p0 == p1
    }

    override fun areItemsTheSame(p0: Type, p1: Type): Boolean {
        return p0.id == p1.id
    }
}
