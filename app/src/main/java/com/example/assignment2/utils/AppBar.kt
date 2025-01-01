package com.example.assignment2.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(appbarState.title) },
        actions = appbarState.actions,
        modifier = appbarState.modifier,
        navigationIcon = appbarState.navigationIcon
    )
}