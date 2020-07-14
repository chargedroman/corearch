package com.roman.basearch.utility

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.text.format.DateUtils
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 2019-12-24
 */

class TextLocalizationImpl(val context: Context):
    TextLocalization {

    override fun getString(resourceId: Int): String {

        return try {
            context.getString(resourceId)
        } catch (e: NotFoundException) {
            ""
        }
    }

    override fun getString(resourceId: Int, vararg args: String): String {

        return try {
            context.getString(resourceId, *args)
        } catch (e: NotFoundException) {
            ""
        }
    }

    override fun formatDecimal(decimal: Double): String {
        val long = decimal.toLong()
        val fraction = decimal - long

        return if(fraction > 0) {
            decimal.toString()
        } else {
            long.toString()
        }
    }

    override fun getSimpleDate(date: Date): String {
        val result = DateUtils.getRelativeTimeSpanString(context, date.time)
        return result.toString()
    }

    override fun getDateDifferenceToToday(date: Date): String {
        val time = date.time
        val now = Date().time
        return DateUtils.getRelativeTimeSpanString(time, now, MINUTE_IN_MILLIS).toString()
    }

}
