package com.it2161.dit99999x.assignment1

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
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
import com.it2161.dit99999x.assignment1.ui.components.LoginScreen
import com.it2161.dit99999x.assignment1.ui.components.RegisterUserScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.it2161.dit99999x.assignment1.data.Comments
import com.it2161.dit99999x.assignment1.data.MovieItem
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.components.CommentMovieScreen
import com.it2161.dit99999x.assignment1.ui.components.LandingScreen
import com.it2161.dit99999x.assignment1.ui.components.MovieDetailScreen
import com.it2161.dit99999x.assignment1.ui.components.ProfileScreen
import org.w3c.dom.Comment

enum class MovieScreen(@StringRes val title: Int) {
    Login(R.string.login),
    Landing(R.string.app_name),
    Comment(R.string.comment_movie),
    Detail(R.string.movie_detail),
    Profile(R.string.profile_screen),
    Register(R.string.register_user),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieViewerApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MovieScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieScreen.Login.name
    )
    val (sharedMovieItem, setSharedMovieItem) = remember { mutableStateOf(
        MovieItem("", "", "", 0.0f, listOf(""), "", "", 0, "", listOf(Comments("", "", "", "")))) }
    val (sharedCommentItem, setSharedCommentItem) = remember { mutableStateOf( Comments("", "", "", "") ) }

    Scaffold(
        topBar = {
            // TODO: Add for other screens stuff that needs custom app bars :)
            var (expanded, setExpanded) = remember { mutableStateOf(false) }
            val currentScreenTitle = currentScreen.title
            if (currentScreenTitle != MovieScreen.Login.title &&
                currentScreenTitle != MovieScreen.Register.title) {
                CenterAlignedTopAppBar(
                    title = {
                        if (currentScreenTitle != MovieScreen.Detail.title) {
                            Text (
                                text = stringResource(currentScreenTitle),
                                style = TextStyle(fontSize = 18.sp)
                            )
                        } else {
                            Text (
                                text = sharedMovieItem.title,
                                style = TextStyle(fontSize = 18.sp)
                            )
                        }
                    },
                    navigationIcon = {
                        if (currentScreenTitle == MovieScreen.Detail.title) {
                            IconButton(onClick = { navController.navigate(MovieScreen.Landing.name)}) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                        else if (currentScreenTitle == MovieScreen.Comment.title) {
                            IconButton(onClick = { navController.navigate(MovieScreen.Detail.name)}) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
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
                        if (currentScreenTitle == MovieScreen.Detail.title) {
                            IconButton(onClick = { setExpanded(true)}) {
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
//                                        navController.navigate(MovieScreen.Comment.name)
                                    }
                                ) {
                                    Text("Add Comments")
                                }
                            }
                        }
                    }
                )
            }
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
                        setMovieItem = setSharedMovieItem,
                        onClickMovieItem = {
                            navController.navigate(MovieScreen.Detail.name)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(route = MovieScreen.Profile.name) {
                    ProfileScreen()
                }
                composable(
                    route = MovieScreen.Detail.name
                ){ backStackEntry ->
                    MovieDetailScreen(
                        sharedMovieItem = sharedMovieItem,
                        setSharedCommentItem = setSharedCommentItem,
                        onClickCommentItem = {
                            navController.navigate(MovieScreen.Comment.name)
                        }
                    )
                }
                composable(route = MovieScreen.Comment.name) {
                    CommentMovieScreen(
                        sharedCommentItem = sharedCommentItem
                    )
                }
            }
        }
    }
}

//composable(
//route = MovieScreen.Detail.name + "/{movieId}" // Ensure argument name matches
//) { backStackEntry ->
//    val movieId = backStackEntry.arguments?.getInt("movieId")
//        ?: throw IllegalArgumentException("Missing movieId argument") // Handle missing argument
//
//    MovieDetailScreen(
//        movieId = movieId
//    )
//}