package tn.foxia.ebookapp.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tn.foxia.ebookapp.model.Book
import tn.foxia.ebookapp.utils.DetailViewState
import tn.foxia.ebookapp.utils.ViewState


class MainViewModel : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _detailViewState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)

    val books = _viewState.asStateFlow()
    val bookDetails = _detailViewState.asStateFlow()

    // Helps to format the JSON
    val format = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    // get all the data from the Book.json
    fun getAllBooks(context: Context) {
        try {
            // read JSON file
            val myJson = context.assets.open("books.json").bufferedReader().use {
                it.readText()
            }

            // format JSON
            val bookList = format.decodeFromString<List<Book>>(myJson)
            _viewState.value = ViewState.Success(bookList)
        } catch (e: java.lang.Exception) {
            _viewState.value = ViewState.Error(e)
        }
    }

    // get book by ID
    fun getBookByID(context: Context, isbnNO:String) = viewModelScope.launch {
        try {
            // read JSON file
            val myJson = context.assets.open("books.json").bufferedReader().use {
                it.readText()
            }

            // format JSON
            val book = format.decodeFromString<List<Book>>(myJson)
                .filter {
                    it.isbn.contentEquals(isbnNO)
                }.first()
            _detailViewState.value = DetailViewState.Success(book)
        } catch (e: java.lang.Exception) {
            _detailViewState.value = DetailViewState.Error(e)
        }
    }

}