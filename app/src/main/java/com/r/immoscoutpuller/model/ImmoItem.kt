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


    abstract val id: String
    abstract val dataTypeHashCode: Int

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


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ImmoItem) return false

        if (id != other.id) return false
        if (warmRent != other.warmRent) return false
        if (rooms != other.rooms) return false
        if (livingSpace != other.livingSpace) return false
        if (title != other.title) return false
        if (inserted != other.inserted) return false
        if (lastModified != other.lastModified) return false
        if (dataTypeHashCode != other.dataTypeHashCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + warmRent.hashCode()
        result = 31 * result + rooms.hashCode()
        result = 31 * result + livingSpace.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + inserted.hashCode()
        result = 31 * result + lastModified.hashCode()
        result = 31 * result + dataTypeHashCode.hashCode()
        return result
    }

}
