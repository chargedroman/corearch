package com.r.immoscoutpuller.background

import com.r.immoscoutpuller.immoscout.ImmoScoutRepository
import com.r.immoscoutpuller.immoscout.model.ImmoItemResponse
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.utility.TextLocalization
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class PullWorkPresenter: KoinComponent {

    companion object {
        const val KEY_IMMO_LIST = "immoItems"
    }

    val localRepository: LocalRepository by inject()
    val textLocalization: TextLocalization by inject()
    val immoScoutRepository: ImmoScoutRepository by inject()


    fun start() {


    }

    fun getLastResult() {
        localRepository.readFile<List<ImmoItemResponse>>("")
    }

    fun getFreshResult() {

    }

}
