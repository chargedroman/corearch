package com.roman.basearch.arch

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

class AutoClearedValue<AnyValue : Any> : ReadWriteProperty<Fragment, AnyValue>, LifecycleObserver {

    private var value: AnyValue? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): AnyValue =
        value ?: throw IllegalStateException("Trying to call an auto-cleared value outside of the view lifecycle.")

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: AnyValue) {
        thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
        this.value = value
        thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        value = null
    }

}
