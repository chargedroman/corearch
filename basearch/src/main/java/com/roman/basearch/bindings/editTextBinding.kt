package com.roman.basearch.bindings

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.roman.basearch.arch.adapters.AdapterAfterTextChanged
import com.roman.basearch.arch.adapters.AdapterCustomActionDone
import com.roman.basearch.arch.adapters.AdapterTextListener
import com.roman.basearch.arch.listeners.ClickListener
import com.roman.basearch.arch.listeners.TextListener
import com.roman.basearch.utility.store.KeyValueStore

/**
 *
 * Author: romanvysotsky
 * Created: 11.07.23
 */

const val KEY_UP_DEBOUNCE_TIME = 300L


@BindingAdapter(value = ["bindEditTextFocus", "bindFocusDelay"], requireAll = false)
fun bindEditTextFocus(editText: TextInputEditText, focus: Boolean?, delay: Long = KEY_UP_DEBOUNCE_TIME) {
    if (focus == null || focus == false) {
        return
    }

    if (editText.requestFocus()) {
        val manager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as? InputMethodManager

        editText.postDelayed({
            manager?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }, delay)
    }
}

@BindingAdapter("bindCustomActionDone")
fun bindCustomActionDone(editText: TextInputEditText, listener: ClickListener?) {
    if (listener == null) {
        return
    }

    editText.imeOptions = EditorInfo.IME_ACTION_DONE
    val adapter = AdapterCustomActionDone(listener)
    editText.setOnEditorActionListener(adapter)
}

@BindingAdapter("bindTextChanged")
fun bindTextChanged(editText: TextInputEditText, listener: TextListener?) {
    if (listener == null) {
        return
    }

    val adapter = AdapterAfterTextChanged(listener)
    editText.addTextChangedListener(adapter)
}

@BindingAdapter(value = ["bindStore", "bindStoreKey"], requireAll = false)
fun bindStore(editText: TextInputEditText, store: KeyValueStore?, key: String?) {
    if (store == null || key == null) {
        return
    }

    val currentText = store.getString(key, null) ?: ""
    editText.setText(currentText)

    val listener = AdapterTextListener {
        store.edit { putString(key, it) }
    }

    val adapter = AdapterAfterTextChanged(listener)
    editText.addTextChangedListener(adapter)
}
