package com.r.immoscoutpuller.bindings

import android.text.Editable
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.viewmodel.getKoinInstance

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */


interface TextChanged {
    fun onChanged(text: String)
}


/**
 * Just a quick utility binding for syncing EditText text with LocalRepository via one key
 */
@BindingAdapter("bindTextToLocalRepositoryKey")
fun bindTextToLocalRepositoryKey(editText: EditText, key: String?) {
    if(key == null) {
        return
    }

    val localRepository = getKoinInstance<LocalRepository>()
    val text = localRepository.retrieve(key) ?: key
    val editable = Editable.Factory.getInstance().newEditable(text)
    editText.text = editable

    editText.addTextChangedListener(afterTextChanged = {
        val value = it?.toString() ?: key
        localRepository.save(key, value)
    })
}

@BindingAdapter("bindTextChanged")
fun bindTextChanged(editText: EditText, listener: TextChanged?) {
    if(listener == null) {
        return
    }

    editText.addTextChangedListener(afterTextChanged = {
        val value = it?.toString() ?: ""
        listener.onChanged(value)
    })
}

@BindingAdapter("bindCheckBoxToLocalRepositoryKey")
fun bindCheckBoxToLocalRepositoryKey(checkBox: CheckBox, key: String?) {
    if(key == null) {
        return
    }

    val localRepository = getKoinInstance<LocalRepository>()
    val isChecked = (localRepository.retrieve(key) ?: key).toBoolean()

    checkBox.isChecked = isChecked
    checkBox.setOnCheckedChangeListener { _, value ->
        localRepository.save(key, value.toString())
    }
}
