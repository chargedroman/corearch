package com.r.immoscoutpuller.immoscout

import com.google.gson.Gson
import com.r.immoscoutpuller.immoscout.model.PagingResponse
import okhttp3.Response
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class ImmoScoutParserImpl: ImmoScoutParser {

    private val converter = Gson()


    override fun extractPagingResponseFrom(response: Response): PagingResponse {

        val rawHtml = response.body()?.string() ?: ""
        val pagingDataStartIndex = rawHtml.indexOf("{\"paging\"")

        val substring = if(pagingDataStartIndex < 0) "" else rawHtml.substring(pagingDataStartIndex)
        val pagingJson = getSubstringUntilFirstBracketCloses(substring) ?: ""

        return converter.fromJson(pagingJson, PagingResponse::class.java)
    }


    private fun getSubstringUntilFirstBracketCloses(data: String): String? {
        val stack = Stack<Char>()

        for((index, ch) in data.withIndex()) {

            if(ch == '{') {
                stack.push('{')
            }

            if(ch == '}') {

                if(stack.size == 0) {
                    return data.substring(0, index)
                }

                stack.pop()
            }

        }

        return null
    }

}
