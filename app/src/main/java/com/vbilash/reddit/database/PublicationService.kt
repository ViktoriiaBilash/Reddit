package com.vbilash.reddit.database

import com.vbilash.reddit.model.Publication

class PublicationService {

    private var publicationsList = mutableListOf<Publication>()

    init {
    fillPublicationList()
    }

    private fun fillPublicationList() {
        for(n in 0..20){
            publicationsList.add(Publication(n, "https://i.pravatar.cc/50?img=$n", "Fake name", n, "some text", "https://i.pravatar.cc/500?img=$n", n))
        }
    }

    fun getPublicationsList() : MutableList<Publication>{
        return publicationsList
    }
}