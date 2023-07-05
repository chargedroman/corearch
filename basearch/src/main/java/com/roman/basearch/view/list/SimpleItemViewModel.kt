package com.roman.basearch.view.list

import androidx.databinding.ObservableField

/**
 *
 * Author: romanvysotsky
 * Created: 2019-09-16
 */

class SimpleItemViewModel<Item> : BaseItemViewModel<Item>() {

    val item: ObservableField<Item> = ObservableField()

    override fun bindItem(item: Item) {
        this.item.set(item)
    }

}
