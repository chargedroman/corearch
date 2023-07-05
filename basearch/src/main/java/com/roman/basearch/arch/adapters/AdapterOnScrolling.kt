package com.roman.basearch.arch.adapters

import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Author: romanvysotsky
 * Created: 2020-02-28
 */

class AdapterOnScrolling(val onDrag: () -> Unit) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

        if(newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            onDrag()
        }
    }

}
