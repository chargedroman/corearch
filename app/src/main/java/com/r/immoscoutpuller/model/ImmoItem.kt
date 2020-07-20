package com.r.immoscoutpuller.model

import androidx.databinding.ObservableField
import com.r.immoscoutpuller.immoscout.IMMO_ITEM_NOTES_PREFIX
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.view.list.BaseItemViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
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


    abstract fun pojoStringDump(): String


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


    class ViewModel<Type: ImmoItem>: BaseItemViewModel<Type>(), KoinComponent {

        val item: ObservableField<Type> = ObservableField()
        val noteKey: ObservableField<String> = ObservableField()
        val noteText: ObservableField<String> = ObservableField()
        val noteVisible: ObservableField<Boolean> = ObservableField()
        val noteContacted: ObservableField<Boolean> = ObservableField()

        private val localRepository: LocalRepository by inject()

        override fun bindItem(item: Type) {

            val noteKey = IMMO_ITEM_NOTES_PREFIX+item.id
            val noteText = localRepository.retrieve(noteKey) ?: ""

            val contactedKey = noteKey+"c"
            val contacted = (localRepository.retrieve(contactedKey)?: "").toBoolean()

            this.item.set(item)
            this.noteKey.set(noteKey)
            this.noteText.set(noteText)
            this.noteVisible.set(false)
            this.noteContacted.set(contacted)
        }

        fun onCommentTextChanged(text: String) {
            val noteKey = this.noteKey.get() ?: return
            localRepository.save(noteKey, text)
            noteText.set(text)
        }

        fun onContactedCheckChanged(contacted: Boolean) {
            val noteKey = this.noteKey.get() ?: return
            localRepository.save(noteKey+"c", contacted.toString())
            noteContacted.set(contacted)
        }

        fun onShowNoteToggleClicked() {
            val visible = noteVisible.get() ?: false
            noteVisible.set(!visible)
        }

    }

}
