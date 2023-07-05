package com.roman.basearch.view.list

import androidx.databinding.ViewDataBinding

/**
 *
 * Author: romanvysotsky
 * Created: 25.05.22
 */

abstract class BaseHeaderFooterAdapter<ItemBinding : ViewDataBinding>(clickFunction: (Unit) -> Unit) :
    BaseListAdapter<Unit, NoItemDiffUtil, ItemBinding>(
        clickFunction,
        NoItemDiffUtil
    ) {


    enum class Mode {
        SHOW_WHEN_DATA,
        SHOW_WHEN_NO_DATA,
        SHOW_WHEN_UPDATE,
        NOP,
    }


    open val mode: Mode = Mode.NOP
    override val viewModelFactory: () -> BaseItemViewModel<Unit> = { SimpleItemViewModel() }


    fun onDataUpdated(content: List<*>) {
        when(mode) {
            Mode.SHOW_WHEN_DATA -> if(content.isEmpty()) hideContent() else showContent()
            Mode.SHOW_WHEN_NO_DATA -> if(content.isEmpty()) showContent() else hideContent()
            Mode.SHOW_WHEN_UPDATE -> showContent()
            Mode.NOP -> Unit
        }
    }

    fun showContent(isVisible: Boolean) {
        if(isVisible)
            showContent()
        else
            hideContent()
    }

    fun showContent() {
        submitList(listOf(Unit))
    }

    fun hideContent() {
        submitList(emptyList())
    }


}
