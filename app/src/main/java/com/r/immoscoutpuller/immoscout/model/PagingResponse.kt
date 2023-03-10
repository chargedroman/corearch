package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class PagingResponse(
    @SerializedName("description") val description: DescriptionResponse?,
    @SerializedName("paging") val paging: PageDataResponse?,
    @SerializedName("resultlistEntries") val resultListEntries: List<ImmoItemsResponse>?
): Serializable {

    fun getAllImmoItems(): List<ImmoItemResponse> {
        if(resultListEntries == null) {
            return listOf()
        }

        val result = mutableListOf<ImmoItemResponse>()

        for(entry in resultListEntries) {
            result.addAll(entry.immoItems ?: emptyList())
        }

        return result
    }

}
