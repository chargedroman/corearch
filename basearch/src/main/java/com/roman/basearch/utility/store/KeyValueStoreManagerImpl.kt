package com.roman.basearch.utility.store

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

class KeyValueStoreManagerImpl: KeyValueStoreManager {

    override fun get(key: StoreKey): KeyValueStore {
        val name = key.getId().toString()
        return KeyValueStoreImpl(name)
    }

}
