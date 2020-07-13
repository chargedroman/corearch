package com.r.immoscoutpuller.immoscout.model

import com.google.gson.annotations.SerializedName

/**
 *
 * Author: romanvysotsky
 * Created: 13.07.20
 */

data class PagingResponse(
    @SerializedName("description") val description: DescriptionResponse?,
    @SerializedName("paging") val paging: PageDataResponse?,
    @SerializedName("resultlistEntries") val resultListEntries: List<ImmoItemsResponse>?
) {

    fun getAllImmoItems(): List<ImmoItemResponse> {
        if(resultListEntries == null) {
            return listOf()
        }

        val result = mutableListOf<ImmoItemResponse>()

        for(entry in resultListEntries) {
            result.addAll(entry.immoItems)
        }

        return result
    }

}
