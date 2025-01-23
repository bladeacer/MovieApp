package com.example.assignment2.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment2.MyViewModel

@Composable
fun OfflineDetailScreen(
    viewModel: MyViewModel,
) {
    val offlineMovie = viewModel.offlineMovie.collectAsState()

    LaunchedEffect(
        key1 = offlineMovie.value
    ) {
        viewModel.getOfflineMovieById()
        Log.d("DetailScreen", "DetailScreen ${offlineMovie.value}")
        viewModel.getFavouriteMovieById(offlineMovie.value?.id ?: 0)

    }
    Text(
        text = "${offlineMovie.value?.title}",
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
                model = "https://image.tmdb.org/t/p/w185${offlineMovie.value?.posterPath}",
                contentDescription = offlineMovie.value?.title ?: "",
                modifier = Modifier.size(175.dp)
            )

            Spacer(modifier = Modifier.height(45.dp))

            Column {
                Text(
                    text = "Adult: ${offlineMovie.value?.adult}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Original Language: ${offlineMovie.value?.originalLanguage}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Release date: ${offlineMovie.value?.releaseDate}",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Vote count: ${offlineMovie.value?.popularity}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Vote average: ${offlineMovie.value?.voteAverage}",
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
            text = "${offlineMovie.value?.overview}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
