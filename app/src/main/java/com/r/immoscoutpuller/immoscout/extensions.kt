package com.r.immoscoutpuller.immoscout

import com.r.immoscoutpuller.immoscout.model.RentingApartmentsRequest
import com.roman.basearch.utility.LocalRepository

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

fun LocalRepository.getApartmentsRequestSettings(): RentingApartmentsRequest {

    return RentingApartmentsRequest(
        retrieve("minPrice")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxPrice")?.toDoubleOrNull() ?: 0.0,
        retrieve("minSpace")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxSpace")?.toDoubleOrNull() ?: 0.0,
        retrieve("minRooms")?.toDoubleOrNull() ?: 0.0,
        retrieve("maxRooms")?.toDoubleOrNull() ?: 0.0,
        retrieve("geoCodes") ?: ""
    )
}
