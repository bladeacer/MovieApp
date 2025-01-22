package com.example.assignment2.ui.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.assignment2.MyViewModel
import com.example.assignment2.ui.theme.Pink40

@Composable
fun FavDetailScreen(viewModel: MyViewModel, onDelete: () -> Unit) {
    val favDetail = viewModel.favDetail.collectAsState()
    LaunchedEffect(favDetail.value) {
        viewModel.getFavouriteMovieById(viewModel.movieId.value)
    }


    Text(
        text = "${favDetail.value?.title}",
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
                model = "https://image.tmdb.org/t/p/w185${favDetail.value?.posterPath}",
                contentDescription = favDetail.value?.title ?: "",
                modifier = Modifier.size(175.dp)
            )

            Spacer(modifier = Modifier.height(45.dp))

            Column {
                Text(
                    text = "Adult: ${favDetail.value?.adult}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Original Language: ${favDetail.value?.originalLanguage}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Release date: ${favDetail.value?.releaseDate}",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Vote count: ${favDetail.value?.popularity}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Vote average: ${favDetail.value?.voteAverage}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Overview: ",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${favDetail.value?.overview}",
            style = MaterialTheme.typography.bodyMedium
        )

Spacer(modifier = Modifier.height(16.dp))
        Row (
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            TextButton(
                onClick = {
                    viewModel.deleteFavouriteMovieById(favDetail.value?.id ?: 0)
                    onDelete()
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
    }
}