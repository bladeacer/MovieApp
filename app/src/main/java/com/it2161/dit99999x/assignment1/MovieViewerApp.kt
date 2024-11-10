package com.it2161.dit99999x.assignment1

import android.R.attr.content
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.it2161.dit99999x.assignment1.ui.components.LoginScreen
import com.it2161.dit99999x.assignment1.ui.components.RegisterUserScreen
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.components.LandingScreen

enum class MovieScreen(@StringRes val title: Int) {
    Login(R.string.login),
    Landing(R.string.landing),
    Comment(R.string.comment_movie),
    Detail(R.string.movie_detail),
    Profile(R.string.profile_screen),
    Register(R.string.register_user)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar (
    @StringRes currentScreenTitle: Int,
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
//        navigationIcon = {
//            if (canNavigateBack) {
//                Text("Test")
//            }
//        }
    )
}

// Refactor code to use navigation instead of button state

@Preview(showBackground = true)
@Composable
fun MovieViewerApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val userProfile by remember {
        mutableStateOf(UserProfile("TestUser1", "TestPassword1", "abc@gmail.com", "F",
            "1234 5678", false, "2001"))
    }
    val currentScreen = MovieScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieScreen.Login.name
    )
//    val viewModel: MovieViewModel = viewModel()

    Scaffold(
        topBar = {
            MovieAppBar(
                currentScreenTitle = currentScreen.title,
//                canNavigateBack = navController.previousBackStackEntry != null,
//                navigateUp =  { navController.navigateUp() }
            )
        },
        modifier = Modifier
            .fillMaxWidth()

    ) { innerPadding ->

        var modifier = Modifier.fillMaxWidth().padding(innerPadding)
//        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = MovieScreen.Login.name
        ) {
            composable(route = MovieScreen.Login.name) {
                LoginScreen(
                    onLoginButtonClicked = {
                        navController.navigate(MovieScreen.Landing.name)
                    },
                    onRegisterButtonClicked = {
                        navController.navigate(MovieScreen.Register.name)
                    },
                    userProfile = userProfile,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = MovieScreen.Register.name) {
                RegisterUserScreen(
                    onBackButtonClicked = {
                        navController.navigate(MovieScreen.Login.name)
                    },
                    userProfile = userProfile,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = MovieScreen.Landing.name) {
                LandingScreen()
            }
        }

//        when (navState) {
//            0 -> {
//
//                Column (
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(customBg)
//
//                ) {
//                    LoginScreen()
//                    Row {
//                        TextButton(
//                            onClick = {
//                                navState = 1
//                                // TODO: Add handle submission logic
//                                // TODO: Allow user to submit credentials
//                            },
//
//
//                            modifier = Modifier
//                                .offset(x = 35.dp)
//                                .background(Color.Magenta),
//                        ) {
//                            Text(
//                                text ="Login",
//                                fontSize = 17.sp
//                                )
//                        }
//
//                        TextButton(
//                            onClick = {
//                                navState =  2
//                            },
//                            modifier = Modifier
//                                .offset(x = 105.dp)
//                                .background(Color.LightGray)
//                        ) {
//                            Text(
//                                text = "Register",
//                                fontSize = 17.sp
//                            )
//                        }
//                    }
//                }
//            }
//            1 -> {
//                LandingScreen()
//            }
//            2 -> {
//                RegisterUserScreen()
//            }
//            3 -> {
//                ProfileScreen()
//            }
//            // Add edit profile
//            // 4 ->
//            5 -> {
//                MovieDetailScreen()
//            }
//            6 -> {
//                CommentMovieScreen()
//            }
//            // TODO: Add add comment
//
//        }



        // Add conditional button rendering here and their navigation states here
        // Add states and conditionals later to track page flow state

        // Only one use can register is goofy but yeah
    }


}





