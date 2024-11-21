package com.it2161.dit99999x.assignment1.ui.components

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieDetailScreen(
    sharedIndex: Int
){
    key(sharedIndex) {
        Text("$sharedIndex")
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