package com.example.assignment2.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment2.MyViewModel
import com.example.assignment2.utils.Review
import com.example.assignment2.utils.ReviewResult
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.Builder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

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
        Text("ID: ${review.value?.id}")
        Text("Total pages: ${review.value?.totalPages}")
        Text("ID: ${review.value?.totalPages}")
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
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{
                openUrlInCustomTab(context, review?.url ?: "")
            },
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
        Text(review?.content ?: "")
    }
}

private fun openUrlInCustomTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}

//try {
//    val doc: Document = Jsoup.parse(htmlString)
//    Text(
//        text = doc.body().html(),
//        fontSize = 18.sp
//    )
//} catch (e: Exception) {
//    // Handle parsing errors (e.g., display an error message)
//    Text(
//        text = "Error parsing HTML",
//        fontSize = 18.sp
//    )
//}