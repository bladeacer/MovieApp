package com.example.assignment2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment2.MyViewModel
import com.example.assignment2.utils.ReviewResult

@Composable
fun ReviewScreen(
    viewModel: MyViewModel,
) {
    val review = viewModel.review.collectAsState()
    var reviewList = review.value?.results
    LaunchedEffect(
        key1 = review.value
    ) {
        viewModel.fetchReviews()
    }
    Column (
        modifier = Modifier.padding( start = 16.dp, top = 200.dp, end = 16.dp, bottom = 16.dp),
    )
    {
        Text("Page: ${review.value?.page}")
        Text("Total pages: ${review.value?.totalPages}")
        LazyColumn  {
            items(
                count = reviewList?.size ?: 0,
                itemContent = { index ->
                    val reviewItem = reviewList?.get(index)
                    ReviewItem(reviewItem)
                }
            )
        }
    }
}

@Composable
fun ReviewItem(review: ReviewResult?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w185${review?.authorDetails?.avatarPath}",
            contentDescription = review?.authorDetails?.username,
            modifier = Modifier.clip(CircleShape).size(64.dp)
        )
        Text("Name: ${review?.authorDetails?.name ?: "Unknown"}")
        Text("Rating: ${review?.authorDetails?.rating}")
        Text("Username: ${review?.authorDetails?.username}")
        Text("Created at: ${review?.createdAt}")
        Text("Click to open me in a browser.")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Content:\n${review?.content ?: ""}")
    }
}