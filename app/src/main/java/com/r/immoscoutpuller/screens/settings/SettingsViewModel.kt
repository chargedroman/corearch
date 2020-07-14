package com.r.immoscoutpuller.screens.settings

import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import com.r.immoscoutpuller.repository.WorkRepository
import com.roman.basearch.viewmodel.BaseViewModel
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created:
 */

class SettingsViewModel : BaseViewModel() {

    val workRepository: WorkRepository by inject()
    val workData = workRepository.pullWorkLiveData()

    val showStartButton: MutableLiveData<Boolean> = MutableLiveData()
    val showStopButton: MutableLiveData<Boolean> = MutableLiveData()


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
