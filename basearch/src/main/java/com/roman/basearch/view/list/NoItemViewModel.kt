package com.roman.basearch.view.list

import androidx.databinding.ObservableField

/**
 *
 * Author: romanvysotsky
 * Created: 25.08.23
 */

class NoItemViewModel: BaseItemViewModel<Unit>() {

    val item: ObservableField<Unit> = ObservableField()

    override fun bindItem(item: Unit) {
        this.item.set(Unit)
    }

}
