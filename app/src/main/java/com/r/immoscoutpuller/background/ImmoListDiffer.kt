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

        val new = mutableListOf<PresentableImmoItem>()
        val deleted = mutableListOf<PresentableImmoItem>()
        val modified = mutableListOf<PresentableImmoItem>()



        return Diff(new, deleted, modified)
    }


    data class Diff(
        val newItems: List<PresentableImmoItem> = listOf(),
        val deletedItems: List<PresentableImmoItem> = listOf(),
        val modifiedItems: List<PresentableImmoItem> = listOf()
    )

}
