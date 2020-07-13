package com.roman.basearch.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-11
 */

class MarginDecoration(
    private val space: Int,
    private val orientation: Int
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val position: Int = parent.getChildAdapterPosition(view)
        val isLastItem = position == (parent.adapter?.itemCount ?: 0) - 1

        if (orientation == RecyclerView.VERTICAL) {
            outRect.set(space, 0, space, 0)
        }

        if (orientation == RecyclerView.HORIZONTAL) {
            if(isLastItem) {
                outRect.set(0, space, 0, space)
            } else {
                outRect.set(0, space, 0, 0)
            }
        }

    }

}
