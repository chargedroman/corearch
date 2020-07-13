package com.roman.basearch.view.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.roman.basearch.BR

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

class BaseViewHolder<Item, ItemBinding : ViewDataBinding>(
    factory: () -> BaseItemViewModel<Item>,
    dataBinding: ItemBinding,
    clickListener: ItemClickListener<Item>
): RecyclerView.ViewHolder(dataBinding.root) {

    private val viewModel = factory()

    init {
        dataBinding.setVariable(BR.viewModel, viewModel)
        dataBinding.setVariable(BR.clickListener, clickListener)
    }

    fun bind(item: Item) {
        viewModel.bindItem(item)
    }

}
