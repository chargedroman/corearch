package com.roman.basearch.arch.adapters

import android.view.View

/**
 *
 * Author: romanvysotsky
 * Created: 04.04.22
 */

class AdapterOnDetachedFromWindow(val action: () -> Unit): View.OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(v: View) {

    }

    override fun onViewDetachedFromWindow(v: View) {
        action()
    }
}
