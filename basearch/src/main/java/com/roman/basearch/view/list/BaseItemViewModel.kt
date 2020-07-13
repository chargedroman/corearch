package com.roman.basearch.view.list

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

abstract class BaseItemViewModel<Item> : ViewModel(), KoinComponent {

    abstract fun bindItem(item: Item)

}
