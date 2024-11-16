package com.it2161.dit99999x.assignment1.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.derivedStateOf
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
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.R
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.theme.*


@Composable
fun LoginScreen(
    onLoginButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    modifier: Modifier
) {

    val context = LocalContext.current
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
            .padding(35.dp, 400.dp, 0.dp, 0.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = userID,
            onValueChange = { userID = it },
            label = { Text("Enter User ID") },
            keyboardOptions = KeyboardOptions(showKeyboardOnFocus = true),
            modifier = Modifier
                .width(225.dp)

        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .width(225.dp)

            )
            Column (
                modifier = Modifier
                    .padding(10.dp, 4.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.End
            ) {
                TextButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Text(if (passwordVisible) "Hide" else "Show")
                }
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        Row {
            TextButton(
                onClick = {
                    if (MovieRaterApplication.instance.userProfile?.userName.toString() == userID
                        && MovieRaterApplication.instance.userProfile?.password == password)  {
                        showError = false
                        onLoginButtonClicked()
                    }
                    else {
                        showError = true
                    }
                },


                modifier = Modifier
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
                    .padding(69.dp, 0.dp, 0.dp, 0.dp)
                    .background(Pink80)
            ) {
                Text(
                    text = "Register",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }

            if (showError) {
                Text(text="Incorrect user or password", color=Color.Red)
            }
        }
    }
}

//@Preview
//@Composable
//fun LoginUIPreview() {
//    LoginScreen(
//        onLoginButtonClicked = {},
//        onRegisterButtonClicked = {},
//        modifier = Modifier
//    )
//
//}
//
