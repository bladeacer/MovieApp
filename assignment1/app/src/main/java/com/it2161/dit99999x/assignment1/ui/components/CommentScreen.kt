package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.it2161.dit99999x.assignment1.data.Comments
import org.w3c.dom.Comment

@Composable
fun CommentMovieScreen(
    sharedCommentItem: Comments
){
    val commentName = sharedCommentItem.user
    val comment = sharedCommentItem.comment
    val commentDate = sharedCommentItem.date
    val commentTime = sharedCommentItem.time
    val commentDuration = parseDate("$commentDate $commentTime")


}

//@Preview
//@Composable
//fun CommentMovieScreenPreview() {
//
//    CommentMovieScreen()
//}