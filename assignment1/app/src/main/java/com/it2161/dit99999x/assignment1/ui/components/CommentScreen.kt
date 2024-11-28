package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.it2161.dit99999x.assignment1.data.Comments
import com.it2161.dit99999x.assignment1.ui.theme.Pink80
import org.w3c.dom.Comment

@Composable
fun CommentMovieScreen(
    sharedCommentItem: Comments
){
    val commentName = sharedCommentItem.user
    val userInitials = parseInitials(sharedCommentItem.user)
    val comment = sharedCommentItem.comment
    val commentDate = sharedCommentItem.date
    val commentTime = sharedCommentItem.time
    val commentDuration = parseDate("$commentDate $commentTime")

    Column (
        modifier = Modifier
            .padding(35.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Pink80),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    userInitials,
                    color = Color.White,
                    fontSize = 32.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(0.85f)
                    .padding(20.dp, 16.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Text(commentName)
                Text(text = commentDuration)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row (
            modifier = Modifier.fillMaxWidth(0.85f)
        ){
            Text(comment)
        }


    }
}

//@Preview
//@Composable
//fun CommentMovieScreenPreview() {
//
//    CommentMovieScreen()
//}