package com.example.assignment2.ui.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment2.MyViewModel
import com.example.assignment2.utils.Movie
import com.example.assignment2.utils.RequestUrl
import com.example.assignment2.utils.toTitleCase

@Composable
fun LandingScreen(viewModel: MyViewModel, contentPadding: PaddingValues) {
    val movieResponse = viewModel.movieResponse.collectAsState()
    val searchCriteria = viewModel.searchCriteria.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val urls = RequestUrl.entries.toList()

    LaunchedEffect(
        key1 = searchCriteria.value
    ) {
        viewModel.fetchMovies()
    }

    Column (
        modifier = Modifier.padding( start = 16.dp, top = 115.dp, end = 16.dp, bottom = 16.dp)
    ){
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
        Spacer(modifier = Modifier.width(16.dp))
        Row {
            TextField(
                value = searchCriteria.value.pageNumber.toString(),
                onValueChange = { newPageNumber ->
                    viewModel.updatePageNumber(newPageNumber)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "out of ${movieResponse.value?.totalPages}"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = "Total results: ${movieResponse.value?.totalResults}"
            )
        }
        LazyColumn (
            modifier = Modifier.padding(horizontal = contentPadding.calculateBottomPadding(), vertical = 8.dp)
        ){
            items(
                items = movieResponse.value?.results ?: listOf<Movie>(), key = { movie: Movie -> movie.id }, itemContent = {
                        movie ->
                    LandingScreenMovieItem(movie)
                }
            )
        }
    }

}

@Composable
fun LandingScreenMovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
