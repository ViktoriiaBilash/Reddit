package com.vbilash.reddit.adapter

import com.vbilash.reddit.model.Publication

interface PublicationClickListener {
    fun openImage(publication: Publication)

    fun downloadImage(publication: Publication)
}