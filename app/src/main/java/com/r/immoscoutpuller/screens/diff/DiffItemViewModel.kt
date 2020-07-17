package com.r.immoscoutpuller.screens.diff

import androidx.databinding.ObservableField
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.background.ImmoListDiffer
import com.r.immoscoutpuller.model.ImmoItem
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.view.list.BaseItemViewModel
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 17.07.20
 */

class DiffItemViewModel : BaseItemViewModel<ImmoListDiffer.Diff<ImmoItem>>() {

    val textLocalization: TextLocalization by inject()

    val item: ObservableField<ImmoListDiffer.Diff<ImmoItem>> = ObservableField()
    val creationDate: ObservableField<String> = ObservableField()

    val new: ObservableField<String> = ObservableField()
    val deleted: ObservableField<String> = ObservableField()
    val modified: ObservableField<String> = ObservableField()


    val newVisible: ObservableField<Boolean> = ObservableField()
    val deletedVisible: ObservableField<Boolean> = ObservableField()
    val modifiedVisible: ObservableField<Boolean> = ObservableField()

    val newSummary: ObservableField<String> = ObservableField()
    val deletedSummary: ObservableField<String> = ObservableField()
    val modifiedSummary: ObservableField<String> = ObservableField()


    val newDetailVisible: ObservableField<Boolean> = ObservableField()
    val deletedDetailVisible: ObservableField<Boolean> = ObservableField()
    val modifiedDetailVisible: ObservableField<Boolean> = ObservableField()

    val newDetailSummary: ObservableField<String> = ObservableField()
    val deletedDetailSummary: ObservableField<String> = ObservableField()
    val modifiedDetailSummary: ObservableField<String> = ObservableField()


    override fun bindItem(item: ImmoListDiffer.Diff<ImmoItem>) {
        this.item.set(item)

        bindTime(item)
        bindCounts(item)
        bindVisibility(item)
        bindSummaries(item)
        bindDetailSummaries(item)
    }


    fun onNewClicked() {
        val prev = newDetailVisible.get() ?: false
        newDetailVisible.set(!prev)
    }

    fun onDeletedClicked() {
        val prev = deletedDetailVisible.get() ?: false
        deletedDetailVisible.set(!prev)
    }

    fun onModifiedClicked() {
        val prev = modifiedDetailVisible.get() ?: false
        modifiedDetailVisible.set(!prev)
    }


    private fun bindTime(item: ImmoListDiffer.Diff<ImmoItem>) {
        val date = textLocalization.getSimpleDate(item.creationDate)
        val text = textLocalization.getString(R.string.diff_created, date)
        this.creationDate.set(text)
    }

    private fun bindCounts(item: ImmoListDiffer.Diff<ImmoItem>) {
        val new =
            textLocalization.getString(R.string.diff_new, item.newItems.size.toString())
        val deleted =
            textLocalization.getString(R.string.diff_deleted, item.deletedItems.size.toString())
        val modified =
            textLocalization.getString(R.string.diff_modified, item.modifiedItems.size.toString())

        this.new.set(new)
        this.deleted.set(deleted)
        this.modified.set(modified)
    }

    private fun bindVisibility(item: ImmoListDiffer.Diff<ImmoItem>) {
        newVisible.set(item.newItems.isNotEmpty())
        deletedVisible.set(item.deletedItems.isNotEmpty())
        modifiedVisible.set(item.modifiedItems.isNotEmpty())

        newDetailVisible.set(false)
        deletedDetailVisible.set(false)
        modifiedDetailVisible.set(false)
    }

    private fun bindSummaries(items: ImmoListDiffer.Diff<ImmoItem>) {
        val separator = System.lineSeparator()
        var new = ""
        var deleted = ""
        var modified = ""

        for(item in items.newItems) {
            new += item.id + separator + item.title + separator + separator
        }

        for(item in items.deletedItems) {
            deleted += item.id + separator + item.title + separator + separator
        }

        for(item in items.modifiedItems) {
            modified += item.id + separator + item.title + separator + separator
        }

        newSummary.set(new)
        deletedSummary.set(deleted)
        modifiedSummary.set(modified)
    }


    private fun bindDetailSummaries(items: ImmoListDiffer.Diff<ImmoItem>) {
        val separator = System.lineSeparator()
        var new = ""
        var deleted = ""
        var modified = ""

        for(item in items.newItems) {
            new += item.id + separator + item.pojoStringDump() + separator + separator
        }

        for(item in items.deletedItems) {
            deleted += item.id + separator + item.pojoStringDump() + separator + separator
        }

        for(item in items.modifiedItems) {
            modified += item.id + separator + item.pojoStringDump() + separator + separator
        }

        newDetailSummary.set(new)
        deletedDetailSummary.set(deleted)
        modifiedDetailSummary.set(modified)
    }


}
