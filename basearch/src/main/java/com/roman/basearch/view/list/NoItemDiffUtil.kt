package com.roman.basearch.view.list

import androidx.recyclerview.widget.DiffUtil

/**
 *
 * Author: romanvysotsky
 * Created: 07.09.22
 */

object NoItemDiffUtil : DiffUtil.ItemCallback<Unit>() {

    override fun areContentsTheSame(p0: Unit, p1: Unit): Boolean {
        return true
    }

    override fun areItemsTheSame(p0: Unit, p1: Unit): Boolean {
        return true
    }

}
