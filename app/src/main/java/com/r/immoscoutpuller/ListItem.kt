package com.r.immoscoutpuller

import java.util.UUID

/**
 *
 * Author: romanvysotsky
 * Created: 05.07.23
 */

data class ListItem(
    val name: String = UUID.randomUUID().toString(),
    val id: Int = name.hashCode()
)
