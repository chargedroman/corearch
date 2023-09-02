package com.andcharge.core.base.adapters

import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Author: romanvysotsky
 * Created: 31.07.20
 */

class AdapterScrollToTop(val recyclerView: RecyclerView): RecyclerView.AdapterDataObserver() {

    var smoothScroll: Boolean = true


    override fun onChanged() {
        scroll()
    }
    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        scroll()
    }
    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        scroll()
    }
    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        scroll()
    }
    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        scroll()
    }
    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        scroll()
    }


    private fun scroll() {
        if (smoothScroll)
            recyclerView.smoothScrollToPosition(0)
        else
            recyclerView.scrollToPosition(0)
    }

}
