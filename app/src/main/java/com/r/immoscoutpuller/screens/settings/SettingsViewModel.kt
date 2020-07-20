package com.r.immoscoutpuller.screens.settings

import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import com.r.immoscoutpuller.R
import com.r.immoscoutpuller.immoscout.DIFF_PREFIX
import com.r.immoscoutpuller.immoscout.IMMO_SCOUT_ITEMS
import com.r.immoscoutpuller.immoscout.IMMO_WELT_ITEMS
import com.r.immoscoutpuller.repository.WorkRepository
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.utility.TextLocalization
import com.roman.basearch.viewmodel.BaseViewModel
import com.roman.basearch.viewmodel.launch
import kotlinx.coroutines.flow.flatMapConcat
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

        val deleteLogs = SettingsNavigation.ToConfirmDelete(this::onDeleteOldItemsConfirmed)
        navigation.postValue(deleteLogs)
    }

    fun onDeleteOldItemsConfirmed() {

        val flow = localRepository
            .deleteFile(IMMO_SCOUT_ITEMS)
            .flatMapConcat { localRepository.deleteFile(IMMO_WELT_ITEMS) }
            .flatMapConcat { localRepository.deleteAllWithPrefix(DIFF_PREFIX) }

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
