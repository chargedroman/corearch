package com.r.immoscoutpuller.immoscout.presentation

import androidx.databinding.ObservableField
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.roman.basearch.view.list.BaseItemViewModel
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PresentableImmoItem(val pojo: ImmoItemResponse) {


    class ViewModel: BaseItemViewModel<PresentableImmoItem>() {

        val item: ObservableField<PresentableImmoItem> = ObservableField()

        override fun bindItem(item: PresentableImmoItem) {
            this.item.set(item)
        }

    }

}
