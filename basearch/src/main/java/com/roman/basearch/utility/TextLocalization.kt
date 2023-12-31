package com.roman.basearch.utility

import android.content.Context
import java.util.Calendar
import java.util.Date

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

interface TextLocalization {

    //the string resource, or empty if not found
    fun getString(resourceId: Int): String

    //the string resource with args, or empty if not found
    fun getString(resourceId: Int, vararg args: String): String


    fun formatDecimal(decimal: Double): String

    fun getSimpleDate(date: Date): String

    fun getRelativeDate(date: Date): String

    fun getFullDate(date: Date): String

    fun getDateDifferenceToToday(date: Date): String

    fun getCurrentTime(): Calendar


    companion object {
        fun createInstance(context: Context): TextLocalization {
            return TextLocalizationImpl(context)
        }
    }

}
