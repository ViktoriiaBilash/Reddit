package com.vbilash.reddit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vbilash.reddit.model.Publication

class PublicationViewModelFactory (private val publicationList: MutableList<Publication>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PublicationViewModel(publicationList) as T
    }

}