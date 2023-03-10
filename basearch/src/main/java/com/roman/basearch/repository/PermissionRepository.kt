package com.roman.basearch.repository

import androidx.lifecycle.LiveData
import com.roman.basearch.models.Permission

/**
 *
 * Author: romanvysotsky
 * Created: 10.03.23
 */

interface PermissionRepository {

    fun observe(): LiveData<Pair<Permission, (Boolean) -> Unit>>
    fun requestPermission(permission: Permission, callback: (wasGranted: Boolean) -> Unit)
    fun hasPermission(permission: Permission): Boolean

}
