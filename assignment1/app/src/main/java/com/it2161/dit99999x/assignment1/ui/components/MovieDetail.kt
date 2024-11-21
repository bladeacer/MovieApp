package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.data.MovieItem

@Composable
fun MovieDetailScreen(
    sharedMovieItem: MovieItem
){
    val imageString = sharedMovieItem.image
    val bitmap = remember(imageString) {
        MovieRaterApplication.instance.getImgVector(imageString)
    }
    val imageBitmap = remember(bitmap) { bitmap.asImageBitmap() }

    Image(
        bitmap = imageBitmap, contentDescription = "Movie logo",
        modifier = Modifier
            .fillMaxHeight(0.4f)
            .fillMaxWidth(),
        alignment = Alignment.TopCenter
    )
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(35.dp, 350.dp, 0.dp, 0.dp)
    ) {
        Text("Test")
    }

}

//@Preview
//@Composable
//fun MovieDetailScreenPreview() {
//
//
//    MovieDetailScreen( )
//
//}