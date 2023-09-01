package com.roman.basearch.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.roman.basearch.baseextensions.requestLoad
import com.roman.basearch.models.Image

/**
 *
 * Author: romanvysotsky
 * Created: 2020-04-10
 */

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, image: Image?) {
    if(image == null) {
        return
    }

    var request = imageView.requestLoad(image)

    if(image.customOptions != null) {
        request = image.customOptions.invoke(request)
    }

    request
        .format(DecodeFormat.PREFER_RGB_565)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("bindImageUrl")
fun bindImageUrl(imageView: ImageView, imageUrl: String?) {
    if(imageUrl.isNullOrBlank()) {
        return
    }

    bindImage(imageView, Image(imageUrl = imageUrl))
}

@BindingAdapter("bindImageResource")
fun bindImageResource(imageView: ImageView, imageResource: Int?) {
    if(imageResource == null || imageResource == 0) {
        return
    }

    imageView.setImageResource(imageResource)
}
