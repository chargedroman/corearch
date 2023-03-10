package com.roman.basearch.repository

import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.andcharge.mobile.base.architecture.components.SingleLiveEvent
import com.roman.basearch.models.Permission
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 10.03.23
 */

class PermissionRepositoryImpl: KoinComponent, PermissionRepository {

    private val application: Application by inject()
    private val askPermissionLiveData = SingleLiveEvent<Pair<Permission, (Boolean) -> Unit>>()

    override fun observe(): LiveData<Pair<Permission, (Boolean) -> Unit>> {
        return askPermissionLiveData
    }

    override fun hasPermission(permission: Permission): Boolean {
        for(key in permission.keys) {
            if(!hasPermission(key)){
                return false
            }
        }

        return true
    }

    override fun requestPermission(permission: Permission, callback: (wasGranted: Boolean) -> Unit) {
        askPermissionLiveData.postValue(Pair(permission, callback))
    }


    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(application, permission) == PackageManager.PERMISSION_GRANTED
    }

}
