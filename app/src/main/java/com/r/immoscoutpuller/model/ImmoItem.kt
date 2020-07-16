package com.r.immoscoutpuller.model

import androidx.databinding.ObservableField
import com.roman.basearch.view.list.BaseItemViewModel
import org.koin.core.KoinComponent
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

abstract class ImmoItem : KoinComponent, Serializable {

    companion object {
        private val serialVersionUid: Long = 42069
    }


    abstract val id: Long

    abstract val warmRent: String
    abstract val rooms: String
    abstract val livingSpace: String

    abstract val title: String
    abstract val inserted: String
    abstract val lastModified: String


    class ViewModel<Type: ImmoItem>: BaseItemViewModel<Type>() {

        val item: ObservableField<Type> = ObservableField()

        override fun bindItem(item: Type) {
            this.item.set(item)
        }

    }

}
