package com.roman.basearch.arch.adapters

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.roman.basearch.arch.listeners.ClickListener

/**
 *
 * Author: romanvysotsky
 * Created: 11.07.23
 */

class AdapterCustomActionDone(private val listener: ClickListener): TextView.OnEditorActionListener {

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            listener.onClicked()
        }

        return false
    }

}
