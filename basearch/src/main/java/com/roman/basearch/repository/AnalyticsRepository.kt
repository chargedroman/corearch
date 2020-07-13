package com.roman.basearch.repository

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

interface AnalyticsRepository {

    fun logException(error: Throwable)

}
