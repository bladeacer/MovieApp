package com.it2161.dit99999x.assignment1.ui.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.it2161.dit99999x.assignment1.data.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    sharedMovieItem: MovieItem,
    setMovieItem: (MovieItem) -> Unit,
    onClickMovieItem: () -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current
    val movieItems = MovieRaterApplication.instance.data

    LazyColumn {
        items(
            count = movieItems.size,
            itemContent = { index ->
                val movieItem = movieItems[index]
                val imageString = movieItem.image
                val titleString = movieItem.title
                val synopsisString = movieItem.synopsis
                val ratingString = movieItem.ratings_score
                val bitmap = remember(imageString) {
                    MovieRaterApplication.instance.getImgVector(imageString)
                } // Cache bitmap
                val imageBitmap = remember(bitmap) { bitmap.asImageBitmap() }
                Row (
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                        .fillMaxSize()
                        .clickable {
                            setMovieItem(movieItem)
                            onClickMovieItem()
                        }
                ){

                    Image(
                        bitmap = imageBitmap, contentDescription = null,
                        modifier = Modifier
                            .height(150.dp)
                        ,
                        contentScale = ContentScale.Fit
                    )
                    Column (
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                            .padding(20.dp, 0.dp)

                    ) {
                        Text(titleString, fontWeight = FontWeight.Bold)
                        Text(
                            text = synopsisString,
                            overflow = TextOverflow.Ellipsis,
                            softWrap = true,
                            modifier = Modifier.widthIn(max = 185.dp)
                        )
                    }
                    Column (
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                            .padding(10.dp, 0.dp)
                    ) {

                        Text(
                            text = "Rating: $ratingString",
                            modifier = Modifier.widthIn(max = 80.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        )
    }
}

//@Preview
//@Composable
//fun LandingScreenPreview() {
//
//    LandingScreen(
//        onViewProfileButtonClicked = {},
//        onLogoutButtonClicked = {},
//        modifier = Modifier
//    )
//}
