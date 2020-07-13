package com.roman.basearch.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

abstract class BaseListAdapter<Item, ItemDifference : DiffUtil.ItemCallback<Item>, ItemBinding : ViewDataBinding>(
    private val clickListener: ItemClickListener<Item>,
    diffUtil: ItemDifference
) : ListAdapter<Item, BaseViewHolder<Item, ItemBinding>>(diffUtil) {

    protected abstract val viewModelFactory: () -> BaseItemViewModel<Item>
    protected abstract val layoutResourceId: Int

    constructor(clickFunction: (Item) -> Unit, diffUtil: ItemDifference) : this(object :
        ItemClickListener<Item> {
        override fun onItemClicked(item: Item) {
            clickFunction(item)
        }
    }, diffUtil)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): BaseViewHolder<Item, ItemBinding> {

        val dataBinding = onCreateViewDataBinding(viewGroup)
        return BaseViewHolder(viewModelFactory, dataBinding, clickListener)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder<Item, ItemBinding>, i: Int) {
        val item = getItem(i)

        if (item != null) {
            viewHolder.bind(item)
        }
    }

    open fun onCreateViewDataBinding(viewGroup: ViewGroup): ItemBinding {
        val layoutInflater = LayoutInflater.from(viewGroup.context)

        return DataBindingUtil.inflate(
            layoutInflater,
            layoutResourceId,
            viewGroup,
            false
        )
    }

}
