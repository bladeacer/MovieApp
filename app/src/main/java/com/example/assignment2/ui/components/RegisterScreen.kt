package com.example.assignment2.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.assignment2.MyViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment2.data.User
import com.example.assignment2.ui.theme.Pink80
import com.example.assignment2.ui.theme.Purple40
import com.example.assignment2.utils.AppBarState
import kotlinx.coroutines.coroutineScope

@Composable
fun RegisterScreen(
    viewModel: MyViewModel,
    onNavActionClicked: () -> Unit,

) {
    var userName by remember { mutableStateOf("Hint: Enter user name") }
    var password by remember { mutableStateOf("Hint: Enter password") }
    var confirmPassword by remember { mutableStateOf("Hint: Enter password") }
    var email by remember { mutableStateOf("Hint: Enter email") }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(top = 115.dp, start = 35.dp)
    ) {

        Text(
            text = "Enter Preferred Name"
        )
        TextField(
            value = userName,
            onValueChange = { userName = it },
            keyboardOptions = KeyboardOptions(showKeyboardOnFocus = true),
            modifier = Modifier
                .width(225.dp)

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter email"
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                showKeyboardOnFocus = true
            ),
            modifier = Modifier
                .width(270.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Password"
        )

        Row {
            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Text(if (passwordVisible) "Hide" else "Show")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Confirm Password"
        )
        Row {
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                    onClick = { confirmPasswordVisible = !confirmPasswordVisible }
                ) {
                    Text(if (confirmPasswordVisible) "Hide" else "Show")
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextButton(
                onClick = {
                    // Meaning check if they are the default values
                    showToast = true
                    if (userName == "Hint: Enter user name" || password == "Hint: Enter password"
                        || confirmPassword == "Hint: Enter password" || email == "Hint: enter email"
                    ) {
                        // Deny registering if required values not filled up
                        toastMessage = "An empty field was found"
                    } else if (password != confirmPassword) {
                        toastMessage = "Passwords do not match"
                    } else {
                        showToast = false
                        val user = User(name = userName, email = email, password = password)
                        viewModel.addUser(user)
                        onNavActionClicked()
                    }

                },
                modifier = Modifier
                    .background(Purple40)
            ) {
                Text(
                    text = "Register",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }

            TextButton(
                onClick = onNavActionClicked,
                modifier = Modifier
                    .padding(start = 69.dp)
                    .background(Pink80)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }

        }
    }
}