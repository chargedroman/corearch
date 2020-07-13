package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.PagingResponse
import okhttp3.Response

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

interface ImmoScoutParser {

    fun extractPagingResponseFrom(response: Response): PagingResponse

}
