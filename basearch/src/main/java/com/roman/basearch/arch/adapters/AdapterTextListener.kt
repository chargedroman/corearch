package com.roman.basearch.arch.adapters

import com.roman.basearch.arch.listeners.TextListener

/**
 *
 * Author: romanvysotsky
 * Created: 11.07.23
 */

class AdapterTextListener(private val onChanged: (text: String) -> Unit): TextListener {

    override fun onChanged(text: String) {
        onChanged.invoke(text)
    }

}
