package com.example.assignment2.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assignment2.MyViewModel
import com.example.assignment2.data.User

@Composable
fun ProfileScreen(
    viewModel: MyViewModel
) {
    val userProfile = viewModel.user.collectAsState()
    LaunchedEffect(
        key1 = userProfile.value
    ) {
        viewModel.getUser(userProfile.value?.id ?: 0)
    }


    Column (
        modifier = Modifier.padding( start = 16.dp, top = 115.dp, end = 16.dp, bottom = 16.dp)
    ){

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Preferred Name: ${userProfile.value?.name}",
                    color = Color.Black
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "Email: ${userProfile.value?.email}",
                    color = Color.Black
                )
            }
        }
    }
}

