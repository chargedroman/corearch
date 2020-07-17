package com.r.immoscoutpuller.background

import com.r.immoscoutpuller.model.ImmoItem
import java.io.Serializable
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 15.07.20
 */

class ImmoListDiffer<Type: ImmoItem> {
    var lastItems: List<Type> = listOf()
    var freshItems: List<Type> = listOf()


    fun createDiff(): Diff<Type> {

        val lastItems = lastItems
        val freshItems = freshItems

        val new = freshItems.filter { !lastItems.containsItem(it) }
        val deleted = lastItems.filter { !freshItems.containsItem(it) }
        val modified = lastItems.filter { freshItems.containsModifiedItem(it) }

        return Diff(new, deleted, modified)
    }


    private fun List<Type>.containsModifiedItem(item: Type): Boolean {
        val single = singleOrNull { it.id == item.id } ?: return false
        return single != item
    }

    private fun List<Type>.containsItem(item: Type): Boolean {

        for(i in this) {
            if(i.id == item.id) {
                return true
            }
        }

        return false
    }


    data class Diff<Type: ImmoItem>(
        val newItems: List<Type> = listOf(),
        val deletedItems: List<Type> = listOf(),
        val modifiedItems: List<Type> = listOf(),
        val creationDate: Date = Date()
    ): Serializable {

        fun noChanges(): Boolean {
            return newItems.isEmpty() && deletedItems.isEmpty() && modifiedItems.isEmpty()
        }

    }

}
