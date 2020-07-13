package com.roman.basearch.models

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

data class ActionMessage(
    val message: String,
    val actionMessage: String,
    val action: () -> Unit
)
