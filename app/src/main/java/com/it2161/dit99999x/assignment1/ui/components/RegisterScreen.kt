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

@Composable
fun RegisterUserScreen(
    onBackButtonClicked: () -> Unit,
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 35.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextButton(
                onClick = onBackButtonClicked,
                modifier = Modifier.background(Purple40)
            ) {
                Text(
                    text ="Login",
                    color = Color.Gray
                )
            }
        }
    }


}