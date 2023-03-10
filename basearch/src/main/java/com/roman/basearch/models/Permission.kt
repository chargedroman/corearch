package com.roman.basearch.models

import android.Manifest
import androidx.annotation.RequiresApi

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

private const val LOCATION_CODE = 120
private const val CAMERA_CODE = 121
private const val NOTIFICATION_PERMISSION_CODE = 122

enum class Permission(val code: Int, val keys: Array<String>) {

    LOCATION(
        LOCATION_CODE,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    ),

    CAMERA(
        CAMERA_CODE,
        arrayOf(
            Manifest.permission.CAMERA
        )
    ),

    @RequiresApi(33)
    NOTIFICATION(
        NOTIFICATION_PERMISSION_CODE,
        arrayOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
    );

}
