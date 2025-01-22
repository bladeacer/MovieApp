package com.example.assignment2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment2.MyViewModel
import com.example.assignment2.utils.Movie


@Composable
fun SimilarScreen(viewModel: MyViewModel, contentPadding: PaddingValues, onClickMovieItem: () -> Unit) {
    val similarResponse = viewModel.similarResponse.collectAsState()
    LaunchedEffect(
        key1 = similarResponse.value
    ) {
       viewModel.fetchSimilar()
    }

    Column (
        modifier = Modifier.padding( start = 16.dp, top = 135.dp, end = 16.dp, bottom = 16.dp)
    ){

        LazyColumn (
            modifier = Modifier.padding(horizontal = contentPadding.calculateBottomPadding(), vertical = 8.dp)
        ){
            items(
                items = similarResponse.value?.results ?: listOf<Movie>(), key = { movie: Movie -> movie.id }, itemContent = {
                        movie ->
                    LandingScreenMovieItem(movie, viewModel, onClickMovieItem)
                }
            )
        }
    }
}