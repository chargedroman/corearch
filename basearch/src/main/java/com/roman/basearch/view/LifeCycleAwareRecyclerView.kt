package com.roman.basearch.view

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

class LifeCycleAwareRecyclerView: RecyclerView, LifecycleObserver {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, i: Int) : super(context, attributeSet, i)

    fun <T: ViewHolder> setAdapter(lifecycleOwner: LifecycleOwner, adapter: Adapter<T>) {
        lifecycleOwner.lifecycle.addObserver(this)
        this.adapter = adapter
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        this.layoutManager = null
        this.adapter = null
    }

}
