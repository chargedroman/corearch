package com.r.immoscoutpuller.immowelt

import com.google.gson.Gson
import com.r.immoscoutpuller.immowelt.model.ImmoWeltItemResponse
import com.r.immoscoutpuller.util.substringUntilFirstBracketCloses
import okhttp3.Response
import java.util.*

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

class ImmoWeltParserImpl: ImmoWeltParser {

    private val converter = Gson()


    override fun extractImmoItemFrom(id: String, response: Response): ImmoWeltItemResponse {

        val rawHtml = response.body()?.string() ?: ""
        val itemJsonStartIndex = rawHtml.indexOf("{\"Title\"")

        val substring = if(itemJsonStartIndex < 0) "" else rawHtml.substring(itemJsonStartIndex)
        val itemJson = substring.substringUntilFirstBracketCloses() ?: ""

        val item = converter.fromJson(itemJson, ImmoWeltItemResponse::class.java)
        item.id = id
        return item
    }


    override fun extractEstateIdsFrom(response: Response): List<String> {
        val rawHtml = response.body()?.string() ?: ""
        val ids = extractIdsFromRawHtml(rawHtml)
        return tokenizeIds(ids)
    }

    private fun tokenizeIds(ids: String): List<String> {

        val result = mutableListOf<String>()
        val tokenizer = StringTokenizer(ids, ",")

        while(tokenizer.hasMoreTokens()) {
            result.add(tokenizer.nextToken())
        }

        return result
    }

    private fun extractIdsFromRawHtml(rawHtml: String): String {

        val searchString = "id=\"estateIds\" value="
        val index = rawHtml.indexOf(searchString)
        val substring = if(index < 0) "" else rawHtml.substring(index+searchString.length+1)
        val nextIndex = substring.indexOf("\"")
        return if(nextIndex < 0) "" else substring.substring(0, nextIndex)
    }

}
