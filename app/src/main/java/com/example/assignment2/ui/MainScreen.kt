package com.example.assignment2.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.assignment2.MyViewModel
import com.example.assignment2.ui.components.DetailScreen
import com.example.assignment2.ui.components.LandingScreen
import com.example.assignment2.ui.components.LoginScreen
import com.example.assignment2.ui.components.ProfileScreen
import com.example.assignment2.ui.components.RegisterScreen
import com.example.assignment2.utils.AppBarState
import com.example.assignment2.utils.BaseAppBar
import com.example.assignment2.utils.SwitchScreen

@Composable
fun MainScreen(viewModel: MyViewModel = viewModel(factory = MyViewModel.factory)) {
    val navController = rememberNavController()
    val backStackEntryState = navController.currentBackStackEntryAsState()
    val backStackEntry  = backStackEntryState.value
    val currentScreen = SwitchScreen.valueOf(
        backStackEntry?.destination?.route ?: SwitchScreen.Login.name
    )
    var appBarState = remember { mutableStateOf(AppBarState(title = currentScreen.name)) }

    Scaffold(
        topBar = {
            BaseAppBar(appBarState.value)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // TODO: Replace with login route once testing is done
        NavHost(navController = navController, startDestination = SwitchScreen.Landing.name) {
            composable(SwitchScreen.Login.name) {
                LoginScreen(
                    viewModel,
                    {
                        navController.navigate(SwitchScreen.Landing.name)
                    },
                    {
                        navController.navigate(SwitchScreen.Register.name)
                    }
                )
            }
            composable(SwitchScreen.Landing.name) {
                LandingScreen(viewModel, innerPadding)
            }
            composable(SwitchScreen.Register.name) {
                RegisterScreen(viewModel)
            }
            composable(SwitchScreen.ViewProfile.name) {
                ProfileScreen(viewModel)
            }
            composable(SwitchScreen.Detail.name) {
                DetailScreen(viewModel)
            }
        }
    }
}
