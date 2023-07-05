package com.roman.basearch.view.list.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class HorizontalMarginDecoration(
    private val verticalMargin: Int = 0,
    private val horizontalMargin: Int = 0,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val position: Int = parent.getChildAdapterPosition(view)
        val isFirst = position == 0

        if (isFirst) {
            outRect.set(0, verticalMargin, horizontalMargin, verticalMargin)
        } else {
            outRect.set(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
        }

    }

}
