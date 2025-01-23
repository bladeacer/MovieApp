package com.example.assignment2.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.assignment2.MyViewModel
import com.example.assignment2.ui.theme.Pink80
import com.example.assignment2.utils.Movie
import com.example.assignment2.utils.SearchCriteria
import com.example.assignment2.utils.SearchEntry

@Composable
fun SearchScreen(viewModel: MyViewModel, contentPadding: PaddingValues, onClickMovieItem: () -> Unit) {
    val searchResult = viewModel.searchResult.collectAsState()
    val searchKeyword = viewModel.search.collectAsState()
    val searchInput = remember { mutableStateOf("") }
    val context = LocalContext.current
    val isOnline = viewModel.isOnline.collectAsState()
    LaunchedEffect(
        key1 = searchKeyword.value
    ) {
        viewModel.isOnline(context)
        viewModel.fetchSearch()
        Log.d("Search Keyword", "${searchKeyword.value}")
        Log.d("Search Result", "${searchResult.value}")
        Log.d("Search Result", "${viewModel.searchResult.value}")
    }

    Column(
        modifier = Modifier.padding(start = 16.dp, top = 135.dp, end = 16.dp, bottom = 16.dp)
    ) {
        if (isOnline.value) {

            Row {
                TextField(
                    label = {
                        Icon(Icons.Default.Search, "Search Icon")
                    },
                    value = searchInput.value.toString(),
                    onValueChange = { newSearch ->
                        searchInput.value = newSearch
                    },
                    keyboardOptions = KeyboardOptions(
                        showKeyboardOnFocus = true,
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.width(150.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                TextButton(
                    onClick = {
                        viewModel.updateSearch(SearchCriteria(searchInput.value, 1))
                    },
                    modifier = Modifier
                        .padding(start = 69.dp)
                        .background(Pink80)
                ) {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn (
                modifier = Modifier.padding(horizontal = contentPadding.calculateBottomPadding(), vertical = 8.dp)
            ){
                items(
                    items = searchResult.value?.results ?: listOf<Movie>(), key = { movie: Movie -> movie.id }, itemContent = {
                            movie ->
                        LandingScreenMovieItem(movie, viewModel, onClickMovieItem)

                    }
                )
            }

        }
        else {
            Text("No internet connection")
        }

    }
}