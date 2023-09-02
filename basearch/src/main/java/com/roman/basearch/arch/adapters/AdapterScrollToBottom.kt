package com.andcharge.core.base.adapters

import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Author: romanvysotsky
 * Created: 31.07.20
 */

class AdapterScrollToBottom(val recyclerView: RecyclerView): RecyclerView.AdapterDataObserver() {

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
        val itemCount = recyclerView.adapter?.itemCount ?: return
        val scrollTo = itemCount.coerceAtLeast(0)

        if (smoothScroll)
            recyclerView.smoothScrollToPosition(scrollTo)
        else
            recyclerView.scrollToPosition(scrollTo)
    }

}
