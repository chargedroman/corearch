package com.r.immoscoutpuller.background

import com.r.immoscoutpuller.immoscout.presentation.PresentableImmoItem

/**
 *
 * Author: romanvysotsky
 * Created: 15.07.20
 */

class ImmoListDiffer {
    var lastItems: List<PresentableImmoItem> = listOf()
    var freshItems: List<PresentableImmoItem> = listOf()


    fun createDiff(): Diff {

        val lastItems = lastItems
        val freshItems = freshItems

        val new = freshItems.filter { !lastItems.containsItem(it) }
        val deleted = lastItems.filter { !freshItems.containsItem(it) }
        val modified = lastItems.filter { freshItems.containsModifiedItem(it) }

        return Diff(new, deleted, modified)
    }


    private fun List<PresentableImmoItem>.containsModifiedItem(item: PresentableImmoItem): Boolean {
        val single = singleOrNull { it.pojo.id == item.pojo.id } ?: return false
        return single.pojo != item.pojo
    }

    private fun List<PresentableImmoItem>.containsItem(item: PresentableImmoItem): Boolean {

        for(i in this) {
            if(i.pojo.id == item.pojo.id) {
                return true
            }
        }

        return false
    }


    data class Diff(
        val newItems: List<PresentableImmoItem> = listOf(),
        val deletedItems: List<PresentableImmoItem> = listOf(),
        val modifiedItems: List<PresentableImmoItem> = listOf()
    )

}
