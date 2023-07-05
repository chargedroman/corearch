package com.roman.basearch.view.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 *
 * Author: romanvysotsky
 * Created: 23.03.22
 */

class EqualMarginDecoration(
    private val margin: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val position: Int = parent.getChildAdapterPosition(view)
        val isFirstItem = position == 0
        val isLastItem = position == (parent.adapter?.itemCount ?: 0) - 1

        outRect.set(margin, margin, margin, margin)

        if(isFirstItem) {
            outRect.set(0, margin, margin, margin)
        }

        if(isLastItem) {
            outRect.set(margin, margin, 0, margin)
        }

        if(isFirstItem && isLastItem) {
            outRect.set(0, margin, 0, margin)
        }

    }

}
