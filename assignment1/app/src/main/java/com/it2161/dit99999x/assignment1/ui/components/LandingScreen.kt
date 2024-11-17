package com.it2161.dit99999x.assignment1.ui.components
import android.R.attr.fontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import com.it2161.dit99999x.assignment1.data.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    modifier: Modifier
) {
    val context = LocalContext.current
    var movieList = MovieRaterApplication.instance.data
    val scrollState = rememberScrollState()

//            .verticalScroll(scrollState)
    val movieItems = MovieRaterApplication.instance.data

    LazyColumn ()
    {
        items(
            count = movieItems.size,
            itemContent = { index ->
                val movieItem = movieItems[index]
                val imageString = movieItem.image
                val titleString = movieItem.title
                val synopsisString = movieItem.synopsis
                val bitmap = remember(imageString) {
                    MovieRaterApplication.instance.getImgVector(imageString)
                } // Cache bitmap
                val imageBitmap = remember(bitmap) { bitmap.asImageBitmap() }
                Row (
                    modifier = Modifier.padding(10.dp, 0.dp)
                ){
                    // TODO: Make the entire row clickable, how to parse this to a
                    //  movie detail overlay I have not idea
                    Image(
                        bitmap = imageBitmap, contentDescription = null,
                        modifier = Modifier
                            .height(150.dp),
                        contentScale = ContentScale.Fit
                    )
                    Column (
                        modifier = Modifier
                            .widthIn(max = 200.dp)
                            .heightIn(max = 150.dp)
                            .padding(10.dp, 0.dp)

                    ) {
                        Text(titleString, fontWeight = FontWeight.Bold)
                        Text(
                            text = synopsisString,
                            overflow = TextOverflow.Ellipsis,
                            softWrap = true
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