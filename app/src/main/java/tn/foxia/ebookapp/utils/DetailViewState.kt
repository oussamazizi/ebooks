package tn.foxia.ebookapp.utils

import tn.foxia.ebookapp.model.Book

sealed class DetailViewState {
    object Empty: DetailViewState()
    object Loading : DetailViewState()
    data class Success(val data: Book) : DetailViewState()
    data class Error(val exception: Throwable) : DetailViewState()
}