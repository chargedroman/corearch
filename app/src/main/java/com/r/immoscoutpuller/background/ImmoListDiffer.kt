package com.r.immoscoutpuller.background

import com.r.immoscoutpuller.model.PresentableImmoScoutItem

/**
 *
 * Author: romanvysotsky
 * Created: 15.07.20
 */

class ImmoListDiffer {
    var lastItems: List<PresentableImmoScoutItem> = listOf()
    var freshItems: List<PresentableImmoScoutItem> = listOf()


    fun createDiff(): Diff {

        val lastItems = lastItems
        val freshItems = freshItems

        val new = freshItems.filter { !lastItems.containsItem(it) }
        val deleted = lastItems.filter { !freshItems.containsItem(it) }
        val modified = lastItems.filter { freshItems.containsModifiedItem(it) }

        return Diff(new, deleted, modified)
    }


    private fun List<PresentableImmoScoutItem>.containsModifiedItem(item: PresentableImmoScoutItem): Boolean {
        val single = singleOrNull { it.pojo.id == item.pojo.id } ?: return false
        return single.pojo != item.pojo
    }

    private fun List<PresentableImmoScoutItem>.containsItem(item: PresentableImmoScoutItem): Boolean {

        for(i in this) {
            if(i.pojo.id == item.pojo.id) {
                return true
            }
        }

        return false
    }


    data class Diff(
        val newItems: List<PresentableImmoScoutItem> = listOf(),
        val deletedItems: List<PresentableImmoScoutItem> = listOf(),
        val modifiedItems: List<PresentableImmoScoutItem> = listOf()
    ) {

        fun noChanges(): Boolean {
            return newItems.isEmpty() && deletedItems.isEmpty() && modifiedItems.isEmpty()
        }

    }

}
