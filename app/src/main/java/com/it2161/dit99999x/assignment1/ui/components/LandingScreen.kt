package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import com.it2161.dit99999x.assignment1.MovieRaterApplication


@Composable
fun LandingScreen() {

    val context = LocalContext.current
    var movieList = MovieRaterApplication().data

    Column {
        movieList.forEach { item ->
            Text(text = item.image)
            Text(text = item.comment.toString())
            Text(text = item.length.toString())
            Text(text = item.actors.toString())
            Text(text = item.director)
            Text(text = item.ratings_score.toString())
            Text(text = item.title)
            Text(text = item.synopsis)
            Text(text = item.genre)
            Text(text = item.releaseDate)
        }

    }


}

@Preview
@Composable
fun LandingScreenPreview() {
    LandingScreen()
}




