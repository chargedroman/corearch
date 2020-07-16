package com.r.immoscoutpuller.screens.settings

import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.background.worker.ImmoScoutPullWorker
import com.r.immoscoutpuller.model.PresentableImmoScoutItem
import com.r.immoscoutpuller.repository.WorkRepository
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.viewmodel.BaseViewModel
import com.roman.basearch.viewmodel.launch
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created:
 */

class SettingsViewModel : BaseViewModel() {

    val workRepository: WorkRepository by inject()
    val localRepository: LocalRepository by inject()
    val textLocalization: TextLocalization by inject()
    val workData = workRepository.pullWorkLiveData()

    val showStartButton: MutableLiveData<Boolean> = MutableLiveData()
    val showStopButton: MutableLiveData<Boolean> = MutableLiveData()


    fun onDeleteOldItemsClicked() {

        val flow =
            localRepository.saveFile(ImmoScoutPullWorker.KEY_IMMO_LIST, listOf<PresentableImmoScoutItem>())

        launch(
            flow,
            { message.postValue(textLocalization.getString(R.string.settings_delete_success)) },
            { message.postValue(textLocalization.getString(R.string.settings_delete_fail)) }
        )
    }

    fun workInfoUpdated(list: List<WorkInfo>) {
        if(canStartPullWork(list)) {
            showStartButton.postValue(true)
            showStopButton.postValue(false)
        } else {
            showStartButton.postValue(false)
            showStopButton.postValue(true)
        }
    }

    fun onStartPullingClicked() {
        workRepository.startPullingWork()
    }

    fun onStopPullingClicked() {
        workRepository.stopPullingWork()
    }


    private fun canStartPullWork(list: List<WorkInfo>): Boolean {
        if(list.isEmpty()) return true
        val info = list[0]
        return info.state.isFinished
    }

}
