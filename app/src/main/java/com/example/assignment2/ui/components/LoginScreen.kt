package com.example.assignment2.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.assignment2.MyViewModel
import com.example.assignment2.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment2.ui.theme.Pink80
import com.example.assignment2.ui.theme.Purple40
import com.example.assignment2.ui.theme.PurpleGrey40
import com.example.assignment2.utils.NetworkCheck
import kotlinx.coroutines.delay
import kotlin.coroutines.suspendCoroutine

@Composable
fun LoginScreen(
    viewModel: MyViewModel,
    onLoginButtonClick: () -> Unit,
    onRegisterButtonClick: () -> Unit
) {
    var password = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    var showError = remember { mutableStateOf(false) }
    val user = viewModel.user.collectAsState()
    val allowAction = viewModel.allowAction.collectAsState()
    var loadingStart = remember {mutableStateOf(false)}
    var loadingEnd = remember {mutableStateOf(false)}

    Image(
        painter = painterResource(R.drawable.movie_viewer_logo),
        contentDescription = "Movie logo",
        modifier = Modifier.fillMaxWidth().padding(top = 125.dp).height(300.dp),
    )

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start =75.dp,top=435.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Enter Email") },
            keyboardOptions = KeyboardOptions(showKeyboardOnFocus = true),
            modifier = Modifier
                .width(225.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Enter Password") },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .width(225.dp)

            )
            Column(
                modifier = Modifier
                    .padding(10.dp, 4.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.End
            ) {
                TextButton(
                    onClick = { passwordVisible.value = !passwordVisible.value }
                ) {
                    Text(if (passwordVisible.value) "Hide" else "Show")
                }
            }

        }
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            TextButton(
                onClick = {
                    viewModel.validateLogin(email.value, password.value)
                    if (user.value != null && allowAction.value == true) {
                        showError.value = false
                        onLoginButtonClick()
                    }
                    else {
                        showError.value = true
                    }
                },
                modifier = Modifier
                    .background(Purple40)
            ) {
                Text(
                    text = "Login",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
            TextButton(
                onClick = onRegisterButtonClick,
                modifier = Modifier
                    .padding(start =69.dp)
                    .background(Pink80)
            ) {
                Text(
                    text = "Register",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }

        }
        LoginError(showError.value)
    }
}

@Composable
private fun LoginError(shown: Boolean) {
    val context = LocalContext.current
    if (shown) {
        Toast.makeText(context, "Incorrect user or Password", Toast.LENGTH_SHORT).show()
    }
}