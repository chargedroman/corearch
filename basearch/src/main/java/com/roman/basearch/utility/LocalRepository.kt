package com.roman.basearch.utility

import kotlinx.coroutines.flow.Flow

/**
 * just a shared prefs abstraction
 *
 * Author: romanvysotsky
 * Created: 06.07.20
 */

interface LocalRepository {

    fun save(key: String, value: String)
    fun retrieve(key: String): String?

    fun <Type> saveFile(key: String, type: Type): Flow<Type>
    fun <Type> readFile(key: String): Flow<Type>

}
