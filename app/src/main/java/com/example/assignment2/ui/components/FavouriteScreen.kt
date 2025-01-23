package com.example.assignment2.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment2.MyViewModel
import com.example.assignment2.data.FavouriteMovieTable

@Composable
fun FavouriteScreen(viewModel: MyViewModel, contentPadding: PaddingValues, onClickMovieItem: () -> Unit) {
    val favouriteMovies = viewModel.favouriteMovies.collectAsState()
    val context = LocalContext.current
    val isOnline = viewModel.isOnline.collectAsState()
    LaunchedEffect(
        key1 = favouriteMovies.value
    ) {
        viewModel.isOnline(context)
        viewModel.getFavouriteMovies()
    }

    Column (
        modifier = Modifier.padding( start = 16.dp, top = 135.dp, end = 16.dp, bottom = 16.dp)
    ){

        if (isOnline.value) {
            LazyColumn (
                modifier = Modifier.padding(horizontal = contentPadding.calculateBottomPadding(), vertical = 8.dp)
            ){
                items(
                    count = favouriteMovies.value.size,
                    itemContent = { index ->
                        val movieItem = favouriteMovies.value[index]
                        FavouriteScreenMovieItem(movieItem, viewModel, onClickMovieItem)
                    }

                )
            }
        }
        else {
            Text("No internet connection")
        }

    }
}

@Composable
fun FavouriteScreenMovieItem(movie: FavouriteMovieTable, viewModel: MyViewModel, onClickMovieItem: () -> Unit) {
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
