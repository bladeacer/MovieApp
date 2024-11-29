package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.R

@Composable
fun ProfileScreen(
    sharedAvatarInt: Int
){
    var profileInt = 0
    when (sharedAvatarInt) {
        1 -> {
            profileInt = R.drawable.avatar_1
             }
        2 -> {
            profileInt = R.drawable.avatar_2
        }
        3 -> {
            profileInt = R.drawable.avatar_3
        }

    }
    val userName = MovieRaterApplication.instance.userProfile?.userName
    val email = MovieRaterApplication.instance.userProfile?.email
    val gender = MovieRaterApplication.instance.userProfile?.gender
    val updates = MovieRaterApplication.instance.userProfile?.updates
    val mobile = MovieRaterApplication.instance.userProfile?.mobile
    val yob = MovieRaterApplication.instance.userProfile?.yob

    val updatesString = if (updates == true) {"Yes"} else {"No"}

    Column {
        Image(
            painter = painterResource(profileInt),
            contentDescription = "Profile image",
            modifier = Modifier
                .fillMaxHeight(0.25f)
                .fillMaxWidth(),
            alignment = Alignment.TopCenter
        )
        Column (
            modifier = Modifier
                .padding(35.dp, 32.dp, 0.dp, 0.dp)
        ) {
            Text("User name: $userName")
            Text("Email: $email")
            Text("Gender: $gender")
            Text("Receive updates: $updatesString")
            Text("Mobile number: $mobile")
            Text("Year of birth: $yob")
        }
    }
}

//@Preview
//@Composable
//fun ProfileScreenPreview() {
//
//
//    ProfileScreen()
//
//}