package com.example.assignment2.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.assignment2.ui.theme.Pink40
import com.example.assignment2.ui.theme.Purple80

data class AppBarState(
    val title: String = "",
    val navigationIcon:  @Composable (() -> Unit) = {},
    val actions: @Composable RowScope.() -> Unit = {},
    val modifier: Modifier = Modifier
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseAppBar(appbarState: AppBarState) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Purple80,
            titleContentColor = Color.White,
        ),
        title = { Text(appbarState.title) },
        actions = appbarState.actions,
        modifier = appbarState.modifier,
        navigationIcon = appbarState.navigationIcon,
    )
}