package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.ImmoRequest
import com.roman.basearch.utility.LocalRepository

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

const val IMMO_ITEM_NOTES_PREFIX = "notes_"
const val IMMO_SCOUT_ITEMS = "immoScoutItems"
const val IMMO_WELT_ITEMS = "immoWeltItems"
const val DIFF_PREFIX = "diff_"


fun LocalRepository.getImmoScoutRequestSettings(): ImmoRequest {

    return ImmoRequest(
        retrieve("minPrice")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxPrice")?.toDoubleOrNull() ?: 0.0,
        retrieve("minSpace")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxSpace")?.toDoubleOrNull() ?: 0.0,
        retrieve("minRooms")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxRooms")?.toDoubleOrNull() ?: 0.0,
        retrieve("geoCodesScout") ?: "",
        retrieve("city") ?: ""
    )
}

fun LocalRepository.getImmoWeltRequestSettings(): ImmoRequest {

    return ImmoRequest(
        retrieve("minPrice")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxPrice")?.toDoubleOrNull() ?: 0.0,
        retrieve("minSpace")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxSpace")?.toDoubleOrNull() ?: 0.0,
        retrieve("minRooms")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxRooms")?.toDoubleOrNull() ?: 0.0,
        retrieve("geoCodesWelt") ?: "",
        retrieve("city") ?: ""
    )
}
