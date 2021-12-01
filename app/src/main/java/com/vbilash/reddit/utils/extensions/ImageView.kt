package com.vbilash.reddit.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageWithGlideCircle(url: String) {
    Glide.with(context).load(url).apply(RequestOptions().circleCrop()).into(this)
}

fun ImageView.loadImageWithGlide(url: String) {
    Glide.with(context).load(url).apply(RequestOptions()).into(this)
}