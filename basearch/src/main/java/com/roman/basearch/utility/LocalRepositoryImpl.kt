package com.roman.basearch.utility

import android.content.Context
import android.net.Uri
import androidx.core.content.edit
import kotlinx.coroutines.flow.flow
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

        val result = readFileSync<Type>(key)
        emit(result)

    }

    override fun deleteFile(key: String) = flow {
        deleteFileSync(key)
        emit(Unit)
    }

    override fun <Type> readAllWithPrefix(prefix: String) = flow {

        val files = getAllWithPrefix(prefix)
        val objects = mutableListOf<Type>()

        var i = files.size - 1
        while(i >= 0) {
            objects.add(readFileSync<Type>(files[i]))
            i--
        }

        emit(objects)
    }

    override fun deleteAllWithPrefix(prefix: String) = flow {
        val files = getAllWithPrefix(prefix)
        for(file in files) deleteFileSync(file)
        emit(Unit)
    }


    private fun deleteFileSync(key: String) {
        val filesDir = context.filesDir
        val file = File(filesDir, key)
        file.delete()
    }

    private fun getAllWithPrefix(prefix: String): Array<String> {
        return context.filesDir.list { _, string -> string.startsWith(prefix) }
    }

    private fun <Type> readFileSync(key: String): Type {
        val path = getFileUri(key)
        val stream = context.contentResolver.openInputStream(path)
        val objectStream = ObjectInputStream(stream)
        return objectStream.readObject() as Type
    }

    private fun getFileUri(key: String): Uri {
        val filesDir = context.filesDir
        val file = File(filesDir, key)
        return Uri.fromFile(file)
    }

}
