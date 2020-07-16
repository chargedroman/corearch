package com.r.immoscoutpuller.util

import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

fun String.substringUntilFirstBracketCloses(): String? {
    val stack = Stack<Char>()

    for((index, ch) in this.withIndex()) {

        if(ch == '{') {
            stack.push('{')
        }

        if(ch == '}') {

            if(stack.size == 0) {
                return this.substring(0, index)
            }

            stack.pop()
        }

    }

    return null
}
