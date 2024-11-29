package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.data.Comments
import com.it2161.dit99999x.assignment1.ui.theme.Purple40
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun AddCommentScreen(
    sharedMovieIndex: Int,
    onClickSubmitComment: () -> Unit
){
    val movieItems = MovieRaterApplication.instance.data
    var commentsList = movieItems[sharedMovieIndex].comment
    val imageString = movieItems[sharedMovieIndex].image
    val bitmap = remember(imageString) {
        MovieRaterApplication.instance.getImgVector(imageString)
    } // Cache bitmap
    val imageBitmap = remember(bitmap) { bitmap.asImageBitmap() }
    var userName = MovieRaterApplication.instance.userProfile?.userName.toString()
    var (comment, setComment) = remember { mutableStateOf("") }

    Image(
        bitmap = imageBitmap, contentDescription = null,
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(),
        alignment = Alignment.TopCenter
    )
    Column (
        modifier = Modifier.padding(35.dp, 250.dp, 0.dp, 0.dp)
    ){
        TextField(
            value = comment,
            onValueChange = {setComment(it)},
            label = {Text("Add comment")},
            modifier = Modifier.fillMaxWidth(0.85f).fillMaxHeight(0.4f)
        )
        TextButton(
            onClick = {
                val currentDateTime = LocalDateTime.now()
                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                val date = currentDateTime.format(dateFormatter).toString()
                val time = currentDateTime.format(timeFormatter).toString()
                val newCommentItem = Comments(userName, comment, date, time)
                MovieRaterApplication.instance.data[sharedMovieIndex].comment =
                    (listOf(newCommentItem) + commentsList).sortedByDescending { it.date }
                onClickSubmitComment()
            },
            modifier = Modifier.background(Purple40)
        ) {
            Text("Submit", fontSize = 17.sp, color = Color.White)
        }
    }

}