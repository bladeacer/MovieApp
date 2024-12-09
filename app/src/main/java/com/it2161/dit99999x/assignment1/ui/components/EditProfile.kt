package com.it2161.dit99999x.assignment1.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.it2161.dit99999x.assignment1.R

@Composable
fun EditProfile(
    sharedAvatarInt: Int,
    setSharedAvatarInt: (Int) -> Unit,
    userName: String,
    setUserName: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    passwordVisible: Boolean,
    setPasswordVisible: (Boolean) -> Unit,
    confirmPassword: String,
    setConfirmPassword: (String) -> Unit,
    confirmPasswordVisible: Boolean,
    setConfirmPasswordVisible: (Boolean) -> Unit,
    gender: String,
    setGender: (String) -> Unit,
    updates: String,
    setUpdate: (String) -> Unit,
    email: String,
    setEmail: (String) -> Unit,
    yearOfBirth: String,
    setYearOfBirth: (String) -> Unit,
    options: List<String>,
    showToast: Boolean,
    toastMessage: String,
    mobileNumber: String,
    setMobileNumber: (String) -> Unit
){

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
            onValueChange = {setUserName(it)},
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
                onValueChange = {setPassword(it) },
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
                    onClick = { setPasswordVisible(!passwordVisible) }
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
                onValueChange = {setConfirmPassword(it)},
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
                    onClick = { setConfirmPasswordVisible(!confirmPasswordVisible) }
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
            onValueChange = {setEmail(it)},
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
                onClick = { setGender("Male") },
            )
            Text(
                text = "Female",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
            )
            RadioButton(
                selected = gender == "Female",
                onClick = { setGender("Female") }
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
                onClick = { setGender("Non-Binary") }
            )
            Text(
                text = "Prefer not to say",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
            )
            RadioButton(
                selected = gender == "Prefer not to say",
                onClick = { setGender("Prefer not to say") }
            )
        }


        Text(
            text = "Enter mobile number:"
        )
        TextField (
            value = mobileNumber,
            onValueChange = {setMobileNumber(it)},
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
                    if (updates == "Yes") {
                        setUpdate("No")
                    } else {
                        setUpdate("Yes")
                    }
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
                    setYearOfBirth(it)
                },
            )
        }

        if (showToast) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }

        Row (
            modifier = Modifier
                .padding(0.dp, 0.dp, 8.dp, 0.dp)
        ){
            Image(
                painter = painterResource(R.drawable.avatar_1),
                contentDescription = "Avatar 1",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
                    .size(64.dp)
            )
            RadioButton(
                selected = sharedAvatarInt == 1,
                onClick = { setSharedAvatarInt(1) }
            )
            Image(
                painter = painterResource(R.drawable.avatar_2),
                contentDescription = "Avatar 2",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
                    .size(64.dp)
            )
            RadioButton(
                selected = sharedAvatarInt == 2,
                onClick = { setSharedAvatarInt(2) }
            )
            Image(
                painter = painterResource(R.drawable.avatar_3),
                contentDescription = "Avatar 3",
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
                    .size(64.dp)
            )
            RadioButton(
                selected = sharedAvatarInt == 3,
                onClick = { setSharedAvatarInt(3) }
            )
        }
    }

}
