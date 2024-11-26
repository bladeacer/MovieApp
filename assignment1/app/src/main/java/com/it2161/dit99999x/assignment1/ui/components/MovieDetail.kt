package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.data.Comments
import com.it2161.dit99999x.assignment1.data.MovieItem
import com.it2161.dit99999x.assignment1.ui.theme.Pink80
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.format.DateTimeFormat

@Composable
fun MovieDetailScreen(
    sharedMovieItem: MovieItem,
    setSharedCommentItem: (Comments) -> Unit,
    onClickCommentItem: () -> Unit
){
    val imageString = sharedMovieItem.image
    val bitmap = remember(imageString) {
        MovieRaterApplication.instance.getImgVector(imageString)
    }
    val imageBitmap = remember(bitmap) { bitmap.asImageBitmap() }

    val titleString = sharedMovieItem.title
    val directorString = sharedMovieItem.director
    val genreString = sharedMovieItem.genre
    val dateString = parseDate(sharedMovieItem.releaseDate)

    val lengthInt = sharedMovieItem.length

    val synopsisString = sharedMovieItem.synopsis


    Column (
        modifier = Modifier
            .padding(35.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Image(
            bitmap = imageBitmap, contentDescription = "Movie logo",
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            alignment = Alignment.TopCenter
        )
        Row (
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Title: $titleString", fontSize = 12.sp)
            Text("Director: $directorString", fontSize = 12.sp)
        }
        Row (
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Date: $dateString", fontSize = 12.sp)
            Text("Length: $lengthInt minutes", fontSize = 12.sp)
        }
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Actors ${sharedMovieItem.actors.joinToString(", ").replace("\"", "")
                .replace("[", "").replace("]", "")}", fontSize = 12.sp)
            Text("Genre: $genreString", fontSize = 12.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Synopsis:", fontSize= 12.sp)
            Text(text = synopsisString, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(0.9f))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Comments: ", fontSize= 12.sp)
            val commentsList = sharedMovieItem.comment.sortedByDescending { it.time }
            LazyColumn {
                items(
                    count = commentsList.size,
                    itemContent = { index ->
                        val commentItem = commentsList[index]
                        val userInitials = parseInitials(commentItem.user)
                        val commentName = commentItem.user
                        val comment = commentItem.comment
                        val commentDate = commentItem.date
                        val commentTime = commentItem.time
                        val commentDuration = parseDate("$commentDate $commentTime")

                        Spacer(modifier = Modifier.height(16.dp))
                        Row (
                            modifier = Modifier.clickable{
                                setSharedCommentItem(commentItem)
                                onClickCommentItem()
                            }
                        ) {
                            // Initials
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Pink80),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    userInitials,
                                    color = Color.White,
                                )
                            }
                            Column (
                                modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                            ){
                                Row (
                                    modifier = Modifier.fillMaxWidth(0.85f),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(commentName)
                                    Text(text = commentDuration)
                                }
                                Row (
                                    modifier = Modifier.fillMaxWidth(0.85f)
                                ){
                                    Text(comment)
                                }
                            }
                        }
                    }
                )
            }
        }
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
fun parseDate(dateString: String): String {
    val formatterWithTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    val formatterWithoutTime = DateTimeFormat.forPattern("yyyy-MM-dd")
    val date = try {
        formatterWithTime.parseDateTime(dateString)
    } catch (e: IllegalArgumentException) {
        formatterWithoutTime.parseDateTime(dateString)
    }
    val today = DateTime.now()
    val daysAgo = Days.daysBetween(date, today).days
    val hoursAgo = Hours.hoursBetween(date, today).hours

    return when {
        hoursAgo < 24 -> {
            when (hoursAgo) {
                0 -> "Just now"
                1 -> "1 hour ago"
                else -> "$hoursAgo hours ago"
            }
        }
        daysAgo == 0 -> "Today"
        daysAgo == 1 -> "Yesterday"
        daysAgo > 1 -> "$daysAgo days ago"
        else -> dateString // Fallback if date is in the future
    }
}

fun parseInitials(fullName: String): String {
    var initials: String = ""
    var count: Int = 0
    fullName.forEach { chara ->
        if (chara.isUpperCase() && count < 2) {
            initials += "$chara ."
            count++
        }
    }
    return initials.dropLast(2)
}