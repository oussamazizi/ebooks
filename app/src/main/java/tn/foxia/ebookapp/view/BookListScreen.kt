package tn.foxia.ebookapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tn.foxia.ebookapp.R
import tn.foxia.ebookapp.components.ItemBookList
import tn.foxia.ebookapp.components.TextInputField
import tn.foxia.ebookapp.model.Book
import tn.foxia.ebookapp.navigation.MainActions
import tn.foxia.ebookapp.utils.ViewState
import tn.foxia.ebookapp.viewModel.MainViewModel

@ExperimentalComposeUiApi
@Composable
fun BookListScreen(viewModel: MainViewModel, actions: MainActions) {
    when(val result = viewModel.books.collectAsState().value) {
        ViewState.Loading -> Text(text = "Loading")
        is ViewState.Error -> Text(text = "Error found: ${result.exception}")
        is ViewState.Success -> {
            BookList(result.data, actions)
        }
        ViewState.Empty -> Text(text = "No results found")
    }
}

@ExperimentalComposeUiApi
@Composable
fun BookList(bookList: List<Book>, actions: MainActions) {

    val search = remember {
        mutableStateOf("")
    }
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp),
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        // title
        item {
            Text(
                text = "Explore thousands of \nbooks in go", style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colors.primaryVariant,
                maxLines = 2,
                modifier = Modifier.padding(start = 16.dp, end = 24.dp, bottom = 24.dp)
            )
        }

        // search
        item {
            TextInputField(
                label = stringResource(R.string.text_search),
                value = search.value,
                onValueChanged = {
                    search.value = it
                }
            )
        }

        // search results title
        item {
            Text(
                text = "Famous books",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Start
            )
        }

        // All books list view
        items(bookList.filter { it.title.contains(search.value, ignoreCase = true) }) { book ->
            ItemBookList(book.title, book.authors.toString(), book.thumbnailUrl, book.categories, onItemClick = {
                actions.gotoBookDetails.invoke(book.isbn)
            })
        }
    }
}