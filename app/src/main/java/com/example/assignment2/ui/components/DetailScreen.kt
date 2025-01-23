package com.example.assignment2.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.assignment2.MyViewModel
import com.example.assignment2.ui.theme.Pink40
import com.example.assignment2.ui.theme.Pink80
import com.example.assignment2.ui.theme.Purple40

@Composable
fun DetailScreen(
    viewModel: MyViewModel,
    onNavigateReview: () -> Unit,
    onNavigateSimilar: () -> Unit,
    onFavouriteAction: () -> Unit,
) {
    val movieDetail = viewModel.movieDetail.collectAsState()
    val isFavMovie = viewModel.isFavMovie.collectAsState()

    LaunchedEffect(
        key1 = movieDetail.value
    ) {
        viewModel.fetchDetail()
        Log.d("DetailScreen", "DetailScreen ${movieDetail.value}")
        viewModel.getFavouriteMovieById(movieDetail.value?.id ?: 0)

    }
    Text(
        text = "${movieDetail.value?.title}",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 145.dp, start = 45.dp, end = 20.dp)
    )
    Column (
        modifier = Modifier.padding( start = 16.dp, top = 200.dp, end = 16.dp, bottom = 16.dp),
    )
    {
        Spacer(modifier = Modifier.width(25.dp))
        Row {

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185${movieDetail.value?.posterPath}",
                contentDescription = movieDetail.value?.title ?: "",
                modifier = Modifier.size(175.dp)
            )

            Spacer(modifier = Modifier.height(45.dp))

            Column {
                Text(
                    text = "Adult: ${movieDetail.value?.adult}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Original Language: ${movieDetail.value?.originalLanguage}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Release date: ${movieDetail.value?.releaseDate}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Run time: ${movieDetail.value?.runtime} minutes",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Vote count: ${movieDetail.value?.popularity}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Vote average: ${movieDetail.value?.voteAverage}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
        Text(
            text = "Genres:",
            style = MaterialTheme.typography.bodyLarge,
        )
        movieDetail.value?.genres?.forEach { genre ->
            Text("- ${genre.name}", style=MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Overview: ",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${movieDetail.value?.overview}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row (
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            TextButton(
                onClick = { onNavigateReview() },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .background(Pink80)
            ) {
                Text(
                    text = "View Reviews",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                onClick = { onNavigateSimilar() },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .background(Purple40)
            ) {
                Text(
                    text = "View Similar",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (isFavMovie.value) {

                TextButton(
                    onClick = {
                        viewModel.deleteFavouriteMovieById(movieDetail.value?.id ?: 0)
                        onFavouriteAction()
                    },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .background(Pink40)
                ) {
                    Text(
                        text = "Un-favourite",
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }

            }
            else {

                TextButton(
                    onClick = {
                        viewModel.insertFavouriteMovie(movieDetail.value)
                        onFavouriteAction()
                    },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .background(Pink40)
                ) {
                    Text(
                        text = "Favourite",
                        fontSize = 17.sp,
                        color = Color.White
                    )
                }
            }

        }

    }
}