package com.vbilash.reddit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vbilash.reddit.model.Publication

class PublicationViewModel(private val publicationList: MutableList<Publication>) : ViewModel() {

    var publicationListLiveData = MutableLiveData<MutableList<Publication>>()

    init {
        loadPublicationList()
    }

    private fun loadPublicationList() {
        publicationListLiveData.value = publicationList
    }
}