package com.r.immoscoutpuller.util

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

object NumberFormatter {

    fun formatDecimal(decimal: Double): String {

        val long = decimal.toLong()
        val fraction = decimal - long

        return if(fraction > 0) {
            decimal.toString()
        } else {
            long.toString()
        }
    }

}
