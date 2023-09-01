package com.roman.basearch.arch.adapters

import android.view.View
import android.widget.AdapterView

/**
 *
 * Author: romanvysotsky
 * Created: 26.08.23
 */

@Suppress("UNCHECKED_CAST")
class AdapterOnSpinnerItemSelected<Item>(
    val onItemSelected: (Item, Int) -> Unit
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position) ?: return
        onItemSelected(item as Item, position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}
