package tn.foxia.ebookapp.navigation

import androidx.annotation.StringRes
import tn.foxia.ebookapp.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object BookList : Screen("book_list", R.string.text_bookList)
    object Details : Screen("book_details", R.string.text_bookDetails)
}