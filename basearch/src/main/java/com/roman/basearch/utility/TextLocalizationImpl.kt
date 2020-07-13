package com.roman.basearch.utility

import android.content.Context
import android.content.res.Resources.NotFoundException

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

}
