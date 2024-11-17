package com.it2161.dit99999x.assignment1

import android.util.Log
import android.view.MenuItem
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.it2161.dit99999x.assignment1.ui.components.LoginScreen
import com.it2161.dit99999x.assignment1.ui.components.RegisterUserScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.components.LandingScreen
import com.it2161.dit99999x.assignment1.ui.components.ProfileScreen

enum class MovieScreen(@StringRes val title: Int) {
    Login(R.string.login),
    Landing(R.string.app_name),
    Comment(R.string.comment_movie),
    Detail(R.string.movie_detail),
    Profile(R.string.profile_screen),
    Register(R.string.register_user),
}

// TODO: Remove this default Top Bar, it is only used when debugging

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieViewerApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MovieScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieScreen.Login.name
    )

    Scaffold(
        topBar = {
            // TODO: Add for other screens stuff that needs custom app bars :)
            var (expanded, setExpanded) = remember { mutableStateOf(false) }
            val currentScreenTitle = currentScreen.title
            if (currentScreenTitle != MovieScreen.Login.title && currentScreenTitle != MovieScreen.Register.title) {
                CenterAlignedTopAppBar(
                    title = {
                        Text (
                            text = stringResource(currentScreenTitle),
                            style = TextStyle(fontSize = 18.sp)
                        )
                    },
                    actions = {
                        if (currentScreenTitle == MovieScreen.Landing.title) {
                            IconButton(onClick = { setExpanded(true) }) {
                                Icon(
                                    Icons.Filled.MoreVert,
                                    contentDescription = "More"
                                )
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { setExpanded(false)}
                            ) {
                                TextButton(
                                    onClick = {
                                        navController.navigate(MovieScreen.Profile.name)
                                    }
                                ) {
                                    Text("View Profile")
                                }
                                TextButton(
                                    onClick = {
                                        navController.navigate(MovieScreen.Login.name)
                                    }
                                ) {
                                    Text("Logout")
                                }
                            }
                        }
                    }
                )
            }
//            NavHost(navController = navController, startDestination = MovieScreen.Login.name) {
//                composable (route = MovieScreen.Landing.name) {
//                    CenterAlignedTopAppBar(
//                    )
//                }
//            }
        },

        modifier = Modifier.fillMaxSize()

    ) { innerPadding ->

        var modifier = Modifier.fillMaxSize().padding(innerPadding)
        // TODO: Remove logs after rest of app is made
        Log.d("App data : ", ""+MovieRaterApplication.instance.data.size)
        if(MovieRaterApplication.instance.userProfile != null){
            Log.d("User profile : ", ""+MovieRaterApplication.instance.userProfile!!.userName)
        }
        else{
            Log.d("User profile : ","No user profile saved")
            MovieRaterApplication.instance.userProfile = UserProfile("TestUser1", "TestPassword1", "abc@gmail.com", "F",
                "1234 5678", false, "2001")
        }

        key(MovieRaterApplication.instance.userProfile) {
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
                            navController.navigate(MovieScreen.Landing.name)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(route = MovieScreen.Landing.name) {
                    LandingScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(route = MovieScreen.Profile.name) {
                    ProfileScreen()
                }
            }
        }
    }


}
