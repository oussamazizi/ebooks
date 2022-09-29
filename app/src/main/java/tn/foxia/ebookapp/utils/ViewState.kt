package tn.foxia.ebookapp.utils

import tn.foxia.ebookapp.model.Book

sealed class ViewState {
    object Empty: ViewState()
    object Loading : ViewState()
    data class Success(val data: List<Book>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}