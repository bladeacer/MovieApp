package com.it2161.dit99999x.assignment1

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import android.R.attr.content
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.it2161.dit99999x.assignment1.ui.components.LoginScreen
import com.it2161.dit99999x.assignment1.ui.components.RegisterUserScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
) {
    CenterAlignedTopAppBar(
        title = {
            Text (
                text = stringResource(currentScreenTitle),
                style = TextStyle(fontSize = 18.sp)
            )},
    )
}

@Composable
fun MovieViewerApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    var movieClass = MovieRaterApplication
    movieClass.instance.userProfile = UserProfile("TestUser1", "TestPassword1", "abc@gmail.com", "F",
        "1234 5678", false, "2001")
    val currentScreen = MovieScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieScreen.Login.name
    )


    Scaffold(
        topBar = {
            MovieAppBar(
                currentScreenTitle = currentScreen.title
            )
        },

        modifier = Modifier.fillMaxSize()

    ) { innerPadding ->

        var modifier = Modifier.fillMaxSize().padding(innerPadding)
        Log.d("App data : ", ""+MovieRaterApplication.instance.data.size)
        if(MovieRaterApplication.instance.userProfile != null){
            Log.d("User profile : ", ""+MovieRaterApplication.instance.userProfile!!.userName)
        }
        else{
            Log.d("User profile : ","No user profile saved")
        }

        NavHost(
            navController = navController,
            startDestination = MovieScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MovieScreen.Login.name) {
                LoginScreen(
                    onLoginButtonClicked = {
                        navController.navigate(MovieScreen.Landing.name)
                    },
                    onRegisterButtonClicked = {
                        navController.navigate(MovieScreen.Register.name)
                    },
                    modifier = Modifier.fillMaxSize()

                )
            }
            composable(route = MovieScreen.Register.name) {
                RegisterUserScreen(
                    onBackButtonClicked = {
                        navController.navigate(MovieScreen.Login.name)
                    },
                    onRegisterButtonClicked = {
                        navController.navigate(MovieScreen.Login.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = MovieScreen.Landing.name) {
                LandingScreen()
            }
        }
    }


}





