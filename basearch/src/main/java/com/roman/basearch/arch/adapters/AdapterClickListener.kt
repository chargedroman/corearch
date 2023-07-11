package com.roman.basearch.arch.adapters

import com.roman.basearch.arch.listeners.ClickListener

/**
 *
 * Author: romanvysotsky
 * Created: 11.07.23
 */

class AdapterClickListener(private val onClicked: () -> Unit): ClickListener {

    override fun onClicked() {
        onClicked.invoke()
    }

}
