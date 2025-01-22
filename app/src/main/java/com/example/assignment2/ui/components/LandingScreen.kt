package com.example.assignment2.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.assignment2.MyViewModel
import com.example.assignment2.ui.theme.Pink80
import com.example.assignment2.utils.Movie
import com.example.assignment2.utils.NetworkCheck
import com.example.assignment2.utils.RequestUrl
import com.example.assignment2.utils.SearchCriteria
import com.example.assignment2.utils.toTitleCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LandingScreen(viewModel: MyViewModel, contentPadding: PaddingValues,
                  onClickMovieItem: () -> Unit, onTriggerSearch: () -> Unit) {
    val movieResponse = viewModel.movieResponse.collectAsState()
    val toggleCriteria = viewModel.toggleCriteria.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val searchInput = remember { mutableStateOf("") }
    val urls = RequestUrl.entries.toList()

    LaunchedEffect(
        key1 = toggleCriteria.value
    ) {
        viewModel.fetchMovies()
    }

    Column (
        modifier = Modifier.padding( start = 16.dp, top = 135.dp, end = 16.dp, bottom = 16.dp)
    ){
        Row {
            Row {
                Box {
                    TextButton(
                        onClick = {expanded = true},
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.LightGray, // Background color
                            contentColor = Color.Black // Text color
                        )
                    ) {
                        Text(
                            text = urls[selectedIndex].name.toTitleCase(),
                            modifier = Modifier
                                .padding(8.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown arrow"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        urls.forEachIndexed { index, url ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = url.name.toTitleCase())
                                },
                                onClick = {
                                    selectedIndex = index
                                    viewModel.updateUrl(url.getEndpoint())
                                    expanded = false
                                })
                        }
                    }
                }
            }
            Row {
                Text("Page Number: ")
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = toggleCriteria.value.pageNumber.toString(),
                    onValueChange = { newPageNumber ->
                        viewModel.updatePageNumber(newPageNumber)
                    },
                    keyboardOptions = KeyboardOptions(
                        showKeyboardOnFocus = true,
                        keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(50.dp)
                )
            }
            Text(
                text = "out of ${movieResponse.value?.totalPages ?: ""}"
            )


        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Total results: ${movieResponse.value?.totalResults ?: ""}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextField(
                label = {
                    Icon(Icons.Default.Search, "Search Icon")
                },
                value = searchInput.value,
                onValueChange = {newSearch ->
                    searchInput.value = newSearch
                },
                keyboardOptions = KeyboardOptions(
                    showKeyboardOnFocus = true,
                    keyboardType = KeyboardType.Text),
                modifier = Modifier.width(150.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            TextButton(
                onClick = {
                    viewModel.updateSearch(SearchCriteria(searchInput.value, 1))
                    onTriggerSearch()
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
                items = movieResponse.value?.results ?: listOf<Movie>(), key = { movie: Movie -> movie.id }, itemContent = {
                        movie ->
                    LandingScreenMovieItem(movie, viewModel, onClickMovieItem)

                }
            )
        }
    }



}

// TODO: Navigate and call search from here as well

@Composable
fun LandingScreenMovieItem(movie: Movie, viewModel: MyViewModel, onClickMovieItem: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                enabled = true,
                onClick = {
                    Log.d("Movie Response Sent Id", "${movie.id}")
                    viewModel.updateMovieId(movie.id)
                    onClickMovieItem()
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier.size(125.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = movie.title ?: "Unknown Title",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Popularity: ${movie.popularity}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Language: ${movie.originalLanguage}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Release Date: ${movie.releaseDate}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Average Vote: ${movie.voteAverage}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Popularity: ${movie.voteCount}",
                    style = MaterialTheme.typography.bodySmall
                )

            }
        }
    }
}