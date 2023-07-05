package com.roman.basearch.utility

import kotlinx.coroutines.flow.Flow

/**
 * just a shared prefs abstraction
 *
 * Author: romanvysotsky
 * Created: 06.07.20
 */

interface LocalRepository {

    fun <Type> saveFile(key: String, type: Type): Flow<Type>
    fun <Type> readFile(key: String): Flow<Type>
    fun deleteFile(key: String): Flow<Unit>

    fun <Type> readAllWithPrefix(prefix: String): Flow<List<Type>>
    fun deleteAllWithPrefix(prefix: String): Flow<Unit>

}
