package com.roman.basearch.utility.store

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

interface KeyValueStoreManager {

    fun get(key: StoreKey): KeyValueStore

}
