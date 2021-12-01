package com.vbilash.reddit.model

import android.media.Image

data class Publication(
    val id: Int,
    val icon : String,
    val userName: String,
    val publicationTime: Int,
    val text: String,
    val image: String,
    val commentsNum: Int
)