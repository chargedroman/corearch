package com.roman.basearch.models

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

data class Image(
    val image: Int = 0,
    val imageUrl: String = "",
    val customOptions: ((RequestBuilder<Drawable>) -> RequestBuilder<Drawable>)? = null
) {

    fun isEmpty(): Boolean {
        return image == 0 && imageUrl.isEmpty()
    }
}
