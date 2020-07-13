package com.roman.basearch.arch

import androidx.lifecycle.MutableLiveData

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */


class UpdateAbleLiveData<Type>(private var type: Type): MutableLiveData<Type>(type) {

    fun getCurrentValue(): Type {
        return type
    }

    fun updateValue(update: (Type) -> Unit) {
        val type = type
        update(type)
        postValue(type)
    }

    override fun postValue(value: Type) {
        type = value
        super.postValue(value)
    }

    override fun setValue(value: Type) {
        type = value
        super.setValue(value)
    }

}
