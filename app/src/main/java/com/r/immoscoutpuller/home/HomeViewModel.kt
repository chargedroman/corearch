package com.r.immoscoutpuller.home

import com.roman.basearch.utility.store.KeyValueStore
import com.roman.basearch.utility.store.KeyValueStoreManager
import com.roman.basearch.viewmodel.BaseViewModel
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

class HomeViewModel: BaseViewModel() {

    companion object {
        const val STORE_KEY_NAME = "name"
    }

    val storeManager: KeyValueStoreManager by inject()
    val store: KeyValueStore = storeManager.get()

    val storeKeyName = STORE_KEY_NAME


}
