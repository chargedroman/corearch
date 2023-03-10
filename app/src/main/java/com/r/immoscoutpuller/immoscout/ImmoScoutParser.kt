package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.PagingResponse

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutParser {

    fun extractPagingResponseFrom(rawHtml: String): PagingResponse

}
