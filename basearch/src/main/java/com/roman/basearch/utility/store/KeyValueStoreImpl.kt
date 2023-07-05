package com.roman.basearch.utility.store

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.tencent.mmkv.MMKV

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */
class KeyValueStoreImpl(name: String): KeyValueStore, KeyValueStore.Editor {

    private val store: MMKV = MMKV.mmkvWithID(name, MMKV.MULTI_PROCESS_MODE)

    private var converter: Gson? = null

    private fun getConverter(): Gson {
        val converter = this.converter ?: Gson()
        this.converter = converter
        return converter
    }


    override fun contains(key: String): Boolean {
        return store.containsKey(key)
    }

    override fun getString(key: String, default: String?): String? {
        return store.getString(key, default)
    }

    override fun getStringSet(key: String, default: Set<String>?): Set<String>? {
        return store.getStringSet(key, default)
    }

    override fun getInt(key: String, default: Int): Int {
        return store.getInt(key, default)
    }

    override fun getLong(key: String, default: Long): Long {
        return store.getLong(key, default)
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return store.getBoolean(key, default)
    }

    override fun <T> getObject(key: String, clazz: Class<T>): T? {
        val string = getString(key, null) ?: return null
        return try {
            getConverter().fromJson(string, clazz)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    override fun edit(block: KeyValueStore.Editor.() -> Unit) {
        this.block()
    }


    /*
        Editor
     */

    override fun clear() {
        store.clearAll()
    }

    override fun remove(key: String) {
        store.removeValueForKey(key)
    }

    override fun putString(key: String, value: String?) {
        store.putString(key, value)
    }

    override fun putStringSet(key: String, value: Set<String>?) {
        store.putStringSet(key, value)
    }

    override fun putInt(key: String, value: Int) {
        store.putInt(key, value)
    }

    override fun putLong(key: String, value: Long) {
        store.putLong(key, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        store.putBoolean(key, value)
    }

    override fun <T> putObject(key: String, obj: T) {
        val json = getConverter().toJson(obj)
        putString(key, json)
    }

}