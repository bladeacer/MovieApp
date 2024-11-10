package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.it2161.dit99999x.assignment1.R
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.theme.*


@Composable
fun LoginScreen(
    onLoginButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {

    var password by remember { mutableStateOf("") }
    var userID by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(R.drawable.movie_viewer_logo),
        contentDescription = "Movie logo",
        modifier = Modifier.fillMaxWidth(),
    )

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 35.dp, y=400.dp)
    ) {

        Text(
            text = "Enter user ID",
            color = Color.Gray,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = userID,
            onValueChange = { userID = it },
            label = { Text("User ID") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .width(225.dp)

        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Enter password",
            color = Color.White,
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (showError) {
            Text(
                text = "User ID or password is incorrect",
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .width(225.dp)

        )

        Column (
            modifier = Modifier
                .offset(x = 250.dp, y = -(52.5).dp),
            horizontalAlignment = Alignment.End
        ) {
            TextButton(
                onClick = { passwordVisible = !passwordVisible }
            ) {
                Text(if (passwordVisible) "Hide" else "Show")
            }
        }

        Row {
            TextButton(
                onClick = {
                    if (userProfile.userName == userID && userProfile.password == password)  {
                        showError = false
                        onLoginButtonClicked()
                    }
                    else {
                        showError = true
                    }
                },
                // TODO: Add handle submission logic
                // TODO: Allow user to submit credentials


                modifier = Modifier
                    .offset(x = 35.dp)
                    .background(Purple40),
            ) {
                Text(
                    text = "Login",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }

            TextButton(
                onClick = onRegisterButtonClicked,
                modifier = Modifier
                    .offset(x = 105.dp)
                    .background(Pink40)
            ) {
                Text(
                    text = "Register",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }
}