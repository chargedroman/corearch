package com.roman.basearch.utility

import android.content.Context
import android.net.Uri
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * just a shared prefs abstraction
 *
 * Author: romanvysotsky
 * Created: 06.07.20
 */

class LocalRepositoryImpl(private val context: Context) : LocalRepository {

    companion object {
        private const val KEY = "coolPrefs"
    }

    override fun save(key: String, value: String) {
        val prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        prefs.edit(commit = true) {
            putString(key, value)
        }
    }

    override fun retrieve(key: String): String? {
        val prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }


    /**
     * Suppress the warning because deciding the execution thread is the point of using coroutines..
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    override fun <Type> saveFile(key: String, type: Type) = flow {

        val path = getFileUri(key)
        val outputStream = context.contentResolver.openOutputStream(path)
        val objectOut = ObjectOutputStream(outputStream)

        objectOut.writeObject(type)
        objectOut.flush()
        objectOut.close()

        emit(type)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override fun <Type> readFile(key: String) = flow {

        val path = getFileUri(key)
        val stream = context.contentResolver.openInputStream(path)
        val objectStream = ObjectInputStream(stream)
        val result = objectStream.readObject()
        emit(result as Type)

    }

    private fun getFileUri(key: String): Uri {
        val filesDir = context.filesDir
        val file = File(filesDir, key)
        return Uri.fromFile(file)
    }

}
