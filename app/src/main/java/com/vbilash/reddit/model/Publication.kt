package com.vbilash.reddit.model

import android.media.Image

data class Publication (
    val id : Int,
    val userName : String,
    val publicationTime : Int,
    val text : String,
    val image : Image,
    val commentsNum : Int
        ) {
}