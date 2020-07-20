package com.roman.basearch.dialog

import java.io.Serializable

/**
 *
 * Author: romanvysotsky
 * Created: 20.07.20
 */

data class YesNoDialogParams(
    val yesId: Int,
    val noId: Int,
    val titleId: Int,
    val body: String? = null,
    val yesAction: () -> Unit,
    val noAction: () -> Unit = { }
): Serializable
