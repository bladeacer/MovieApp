package com.it2161.dit99999x.assignment1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.theme.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun CustomDropdownMenu(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false)}
    val baseBg = Color(0xFFebebf6)
    Column (
        modifier = Modifier
            .offset(x = 15.dp, y = -(12.dp))
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
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {

    var userName by remember { mutableStateOf("Hint: Enter user name") }
    var password by remember { mutableStateOf("Hint: Enter password") }
    var confirmPassword by remember { mutableStateOf("Hint: Enter password") }
    var email by remember { mutableStateOf("Hint: Enter email")}
    var gender by remember { mutableStateOf("No selection")}
    var mobileNumber by remember { mutableStateOf("Hint: Enter mobile number")}
    var updates by remember { mutableStateOf(false) }
    var yearOfBirth by remember { mutableStateOf("Select Year of Birth") }
    val toastState = remember {
        SnackbarHostState()
    }
    val options = (1920..2024).reversed().map { it.toString() }
    val scope = rememberCoroutineScope()

    //    Check whether required fields are filled up by seeing if they
    //    are the same as the nonsensical default values, if they are display the toast and refuse
    //    user registration
    // ask gemini about showing toast when some value is true


    Column (
        modifier = Modifier
            .offset(x = 35.dp, y=100.dp)
    ) {

        Text(
            text = "User Name"
        )
        TextField (
            value = userName,
            onValueChange = {userName = it},
            modifier = Modifier
                .width(225.dp)

        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Password"
        )
        TextField (
            value = password,
            onValueChange = {password = it},
            modifier = Modifier
                .width(225.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Confirm Password"
        )
        TextField (
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            modifier = Modifier
                .width(225.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter email"
        )
        TextField (
            value = email,
            onValueChange = {email = it},
            modifier = Modifier
                .width(225.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Select gender"
        )
        Row {
            Text(
                text = "Male",
                modifier = Modifier
                    .offset(y = 12.dp)
            )
            RadioButton(
                selected = gender == "Male",
                onClick = { gender = "Male" },
            )
            Text(
                text = "Female",
                modifier = Modifier
                    .offset(y = 12.dp)
            )
            RadioButton(
                selected = gender == "Female",
                onClick = { gender = "Female" }
            )
        }
        Row (
            modifier = Modifier
                .offset(y = -(8.dp))
        ){
            Text(
                text = "Non-Binary",
                modifier = Modifier
                    .offset(y = 12.dp)
            )
            RadioButton(
                selected = gender == "Non-Binary",
                onClick = { gender = "Non-Binary" }
            )
            Text(
                text = "Prefer not to say",
                modifier = Modifier
                    .offset(y = 12.dp)
            )
            RadioButton(
                selected = gender == "Prefer not to say",
                onClick = { gender = "Prefer not to say" }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row{
            Text (
                text = "Receive updates?"
            )
            Checkbox(
                checked = updates,
                onCheckedChange = { updates = it },
                modifier = Modifier
                    .offset(y = -(12.dp))
            )
        }

        // TODO: Mobile number field

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Year of birth"
            )
            CustomDropdownMenu(
                options = options,
                selectedOption = yearOfBirth,
                onOptionSelected = {
                    yearOfBirth = it
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            TextButton(
                onClick = {
                    // TODO: Handle Register logic, write to UserProfile
                    // Deny registering if required values not filled up
                    // Meaning check if they are the default values
                    onRegisterButtonClicked()
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
                    .offset(x = 69.dp)
                    .background(Pink80)
            ) {
                Text(
                    text = "Back to login",
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }

}
