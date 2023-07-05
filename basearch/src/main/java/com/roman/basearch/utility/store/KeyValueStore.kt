package com.roman.basearch.utility.store

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

interface KeyValueStore {

    fun contains(key: String): Boolean
    fun getString(key: String, default: String?): String?
    fun getStringSet(key: String, default: Set<String>?): Set<String>?
    fun getInt(key: String, default: Int): Int
    fun getLong(key: String, default: Long): Long
    fun getBoolean(key: String, default: Boolean): Boolean
    fun <T> getObject(key: String, clazz: Class<T>): T?
    fun edit(block: Editor.() -> Unit)


    interface Editor {
        fun clear()
        fun remove(key: String)
        fun putString(key: String, value: String?)
        fun putStringSet(key: String, value: Set<String>?)
        fun putInt(key: String, value: Int)
        fun putLong(key: String, value: Long)
        fun putBoolean(key: String, value: Boolean)
        fun <T> putObject(key: String, obj: T)
    }

}
