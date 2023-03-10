package com.r.immoscoutpuller.immoscout

import com.google.gson.Gson
import com.r.immoscoutpuller.immoscout.model.PagingResponse
import com.r.immoscoutpuller.util.substringUntilFirstBracketCloses

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

class ImmoScoutParserImpl: ImmoScoutParser {

    private val converter = Gson()


    override fun extractPagingResponseFrom(rawHtml: String): PagingResponse {
        val pagingDataStartIndex = rawHtml.indexOf("{\"paging\"")

        val substring = if(pagingDataStartIndex < 0) "" else rawHtml.substring(pagingDataStartIndex)
        val pagingJson = substring.substringUntilFirstBracketCloses() ?: ""

        return converter.fromJson(pagingJson, PagingResponse::class.java)
    }

}
