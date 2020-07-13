package com.roman.basearch.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class MarginDecoration(
    private val verticalMargin: Int = 0,
    private val horizontalMargin: Int = 0,
    private val layoutOrientation: Int = RecyclerView.VERTICAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val position: Int = parent.getChildAdapterPosition(view)
        val isLastItem = position == (parent.adapter?.itemCount ?: 0) - 1

        outRect.set(verticalMargin, horizontalMargin, verticalMargin, horizontalMargin)

        if (!isLastItem && layoutOrientation == RecyclerView.HORIZONTAL) {
            outRect.set(verticalMargin, horizontalMargin, 0, horizontalMargin)
        }

        if (!isLastItem && layoutOrientation == RecyclerView.VERTICAL) {
            outRect.set(verticalMargin, horizontalMargin, verticalMargin, 0)
        }

    }

}
