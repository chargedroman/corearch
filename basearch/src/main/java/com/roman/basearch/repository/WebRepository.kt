package com.roman.basearch.repository

import android.content.Context

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

data class WebRepository <T> (
    val context: Context,
    val baseUrlResource: Int,
    val repositoryClass: Class<T>
)
