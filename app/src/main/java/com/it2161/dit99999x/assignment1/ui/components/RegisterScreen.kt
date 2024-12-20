package com.it2161.dit99999x.assignment1.ui.components


import android.util.Log
import androidx.compose.runtime.Composable
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.theme.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextField
import androidx.compose.material3.RadioButton
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.it2161.dit99999x.assignment1.MovieRaterApplication

@Composable
fun CustomDropdownMenu(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false)}
    val baseBg = Color(0xFFebebf6)
    Column (
        modifier = Modifier
            .padding(10.dp, 0.dp, 0.dp, 0.dp)
    )
    {
        Button(
            onClick = {
                expanded = !expanded
            },
            colors = ButtonColors(baseBg, baseBg, baseBg, baseBg)
        ) {
            Text(
                text = selectedOption,
                color = Color.DarkGray,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .fillMaxHeight(0.55f)
        ) {
            options.forEach { option ->
                TextButton(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                ) {
                    Text(
                        text = option,
                        color = Color.DarkGray
                    )
                }

            }
        }
    }
}


@Composable
fun RegisterUserScreen(
    onBackButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    modifier: Modifier
) {
    var userName by remember { mutableStateOf("Hint: Enter user name") }
    var password by remember { mutableStateOf("Hint: Enter password") }
    var confirmPassword by remember { mutableStateOf("Hint: Enter password") }
    var email by remember { mutableStateOf("Hint: Enter email")}
    var gender by remember { mutableStateOf("No selection")}
    var mobileNumber by remember { mutableStateOf("Hint: Enter mobile number")}
    var (updates, setUpdate) = remember { mutableStateOf("Not selected") }
    var yearOfBirth by remember { mutableStateOf("Select Year of Birth") }
    val options = (1920..2024).reversed().map { it.toString() }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    //    Check whether required fields are filled up by seeing if they
    //    are the same as the nonsensical default values, if they are display the toast and refuse
    //    user registration
    // ask gemini about showing toast when some value is true


    Column (
        modifier = Modifier
            .padding(35.dp, 0.dp, 0.dp, 0.dp)
    ) {

        Text(
            text = "User Name"
        )
        TextField (
            value = userName,
            onValueChange = {userName = it},
            keyboardOptions = KeyboardOptions(showKeyboardOnFocus = true),
            modifier = Modifier
                .width(225.dp)

        )

        Spacer(modifier = Modifier.height(8.dp))
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

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Confirm Password"
        )
        Row {
            TextField (
                value = confirmPassword,
                onValueChange = {confirmPassword = it},
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

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter email"
        )
        TextField (
            value = email,
            onValueChange = {email = it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
                showKeyboardOnFocus = true),
            modifier = Modifier
                .width(270.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Select gender"
        )
        Row {
            Text(
                text = "Male",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
            )
            RadioButton(
                selected = gender == "Male",
                onClick = { gender = "Male" },
            )
            Text(
                text = "Female",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
            )
            RadioButton(
                selected = gender == "Female",
                onClick = { gender = "Female" }
            )
        }
        Row (
            modifier = Modifier
                .padding(0.dp, 0.dp, 8.dp, 0.dp)
        ){
            Text(
                text = "Non-Binary",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
            )
            RadioButton(
                selected = gender == "Non-Binary",
                onClick = { gender = "Non-Binary" }
            )
            Text(
                text = "Prefer not to say",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
            )
            RadioButton(
                selected = gender == "Prefer not to say",
                onClick = { gender = "Prefer not to say" }
            )
        }


        Text(
            text = "Enter mobile number:"
        )
        TextField (
            value = mobileNumber,
            onValueChange = {mobileNumber = it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .width(270.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row{
            Text (
                text = "Receive updates?",
                modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp)
            )
            Checkbox(
                checked = updates == "Yes",
                onCheckedChange = {
                    updates = (if (updates == "Yes") {
                        setUpdate("No")
                    } else {
                        setUpdate("Yes")
                    }).toString()
                },
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Year of birth",
                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
            CustomDropdownMenu(
                options = options,
                selectedOption = yearOfBirth,
                onOptionSelected = {
                    yearOfBirth = it
                },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextButton(
                onClick = {
                    // Meaning check if they are the default values
                    showToast = true
                    if (userName == "Hint: Enter user name" || password == "Hint: Enter password"
                        || confirmPassword == "Hint: Enter password" || email == "Hint: enter email"
                        || gender == "No selection" || mobileNumber == "Hint: Enter mobile number"
                        || yearOfBirth == "Select Year of birth"){
                        // Deny registering if required values not filled up
                        toastMessage = "An empty field was found"
                    }
                    else if (password != confirmPassword) {
                        toastMessage = "Passwords do not match"
                    }

                    else {
                        // TODO: Handle Register logic, write to UserProfile
                        val updateVal = updates == "Yes"
                        showToast = false
                        MovieRaterApplication.instance.userProfile = UserProfile(userName,
                            password, email, gender, mobileNumber, updateVal, yearOfBirth)
                        Log.d("User profile : ", ""+MovieRaterApplication.instance.userProfile?.userName)
                        onRegisterButtonClicked()
                    }

                },

                modifier = Modifier
                    .background(Purple40),
            ) {
                Text(
                    text = "Register",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }

            TextButton(
                onClick = onBackButtonClicked,
                modifier = Modifier
                    .padding(69.dp, 0.dp, 0.dp, 0.dp)
                    .background(Pink80)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
            if (showToast) {
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }


}

//@Preview
//@Composable
//fun RegisterUserScreenPreview() {
//    RegisterUserScreen(
//        onBackButtonClicked = {},
//        onRegisterButtonClicked = {},
//        movieClass = MovieRaterApplication,
//        modifier = Modifier
//    )
//
//}