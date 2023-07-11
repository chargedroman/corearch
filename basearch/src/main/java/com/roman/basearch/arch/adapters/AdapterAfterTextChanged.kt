package com.roman.basearch.arch.adapters

import android.text.Editable
import android.text.TextWatcher
import com.roman.basearch.arch.listeners.TextListener

/**
 *
 * Author: romanvysotsky
 * Created: 11.07.23
 */

class AdapterAfterTextChanged(private val listener: TextListener): TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        listener.onChanged(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

}
