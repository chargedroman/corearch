package com.r.immoscoutpuller.settings

import androidx.recyclerview.widget.DiffUtil
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.databinding.ItemListItemBinding
import com.roman.basearch.view.list.BaseItemViewModel
import com.roman.basearch.view.list.BaseListAdapter
import com.roman.basearch.view.list.SimpleItemViewModel

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

class ListItemAdapter(onClick: (ListItem) -> Unit)
    : BaseListAdapter<ListItem, ListItemDiffUtil, ItemListItemBinding>(onClick, ListItemDiffUtil) {

    override val layoutResourceId: Int get() = R.layout.item_list_item
    override val viewModelFactory: () -> BaseItemViewModel<ListItem> =
        { SimpleItemViewModel() }

}

object ListItemDiffUtil : DiffUtil.ItemCallback<ListItem>() {

    override fun areContentsTheSame(p0: ListItem, p1: ListItem): Boolean {
        return p0 == p1
    }

    override fun areItemsTheSame(p0: ListItem, p1: ListItem): Boolean {
        return p0.id == p1.id
    }

}
