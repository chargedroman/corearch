package com.roman.basearch.utility.store

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

class KeyValueStoreManagerImpl: KeyValueStoreManager {

    companion object {
        private const val DEFAULT_STORE_NAME = "default"
    }


    override fun get(): KeyValueStore {
        return KeyValueStoreImpl(DEFAULT_STORE_NAME)
    }


    override fun get(key: StoreKey): KeyValueStore {
        val name = key.getId().toString()
        return KeyValueStoreImpl(name)
    }

}
