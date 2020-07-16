package com.r.immoscoutpuller.immowelt

import com.r.immoscoutpuller.immowelt.model.ImmoWeltItemResponse
import okhttp3.Response

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

interface ImmoWeltParser {

    fun extractEstateIdsFrom(response: Response): List<String>
    fun extractImmoItemFrom(id: String, response: Response): ImmoWeltItemResponse

}
