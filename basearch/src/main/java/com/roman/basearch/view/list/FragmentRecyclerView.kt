package com.roman.basearch.view.list

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.roman.basearch.arch.adapters.AdapterOnDetachedFromWindow

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

class FragmentRecyclerView: RecyclerView, LifecycleObserver {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, i: Int) : super(context, attributeSet, i)


    private var clearAdapterListenerSet: Boolean = false


    fun <T: ViewHolder> setNullableAdapter(adapter: Adapter<T>) {
        this.adapter = adapter
        clearReferenceOnDetach()
    }

    private fun clearReferenceOnDetach() {
        if(clearAdapterListenerSet) {
            return
        }

        val onDetachClearAdapter = AdapterOnDetachedFromWindow { adapter = null }
        addOnAttachStateChangeListener(onDetachClearAdapter)
        clearAdapterListenerSet = true
    }

}
