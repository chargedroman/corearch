package com.roman.basearch.baseextensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.roman.basearch.models.Image

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

private fun ImageView.getRequestManager(): RequestManager {

    val fragment = context.getCurrentFragment()

    return if (fragment != null)
        Glide.with(fragment)
    else
        Glide.with(context)
}

fun ImageView.requestLoad(image: Image)
        : RequestBuilder<Drawable> {

    val manager = getRequestManager()

    return when {
        image.imageUrl.isNotEmpty() ->
            manager
                .load(image.imageUrl)
                .placeholder(image.image)
        else ->
            manager
                .load(image.image)
    }
}
