package com.it2161.dit99999x.assignment1

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.data.Comments
import com.it2161.dit99999x.assignment1.data.MovieItem
import com.it2161.dit99999x.assignment1.data.UserProfile
import com.it2161.dit99999x.assignment1.ui.components.AddCommentScreen
import com.it2161.dit99999x.assignment1.ui.components.CommentMovieScreen
import com.it2161.dit99999x.assignment1.ui.components.EditProfile
import com.it2161.dit99999x.assignment1.ui.components.LandingScreen
import com.it2161.dit99999x.assignment1.ui.components.LoginScreen
import com.it2161.dit99999x.assignment1.ui.components.MovieDetailScreen
import com.it2161.dit99999x.assignment1.ui.components.ProfileScreen
import com.it2161.dit99999x.assignment1.ui.components.RegisterUserScreen

enum class MovieScreen(@StringRes val title: Int) {
    Login(R.string.login),
    Landing(R.string.app_name),
    Comment(R.string.comment_movie),
    Detail(R.string.movie_detail),
    Profile(R.string.profile_screen),
    EditProfile(R.string.edit_profile),
    Register(R.string.register_user),
    AddComment(R.string.add_comment)
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
    val (sharedAvatarInt, setSharedAvatarInt) = remember { mutableIntStateOf(1) }

    var (editProfileUserName, editProfileSetUserName) = remember { mutableStateOf("Hint: Enter user name") }
    var (editProfilePassword, editProfileSetPassword) = remember { mutableStateOf("Hint: Enter password") }
    var (editProfileConfirmPassword, editProfileSetConfirmPassword) = remember { mutableStateOf("Hint: Enter password") }
    var (editProfileEmail, editProfileSetEmail) = remember { mutableStateOf("Hint: Enter email")}
    var (editProfileGender, editProfileSetGender) = remember { mutableStateOf("No selection")}
    var (editProfileMobileNumber, editProfileSetMobileNumber) = remember { mutableStateOf("Hint: Enter mobile number")}
    var (editProfileUpdates, editProfileSetUpdate) = remember { mutableStateOf("Not selected") }
    var (editProfileYearOfBirth,editProfileSetYearOfBirth) = remember { mutableStateOf("Select Year of Birth") }
    val editProfileOptions = (1920..2024).reversed().map { it.toString() }
    var (editProfileShowToast, editProfileSetShowToast) = remember { mutableStateOf(false) }
    var (editProfileToastMessage, editProfileSetToastMessage) = remember { mutableStateOf("") }
    var (editProfilePasswordVisible, editProfileSetPasswordVisible) = remember { mutableStateOf(false) }
    var (editProfileConfirmPasswordVisible, editProfileSetConfirmPasswordVisible) = remember { mutableStateOf(false) }
    var (movieIndex, setMovieIndex) = remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            var (expanded, setExpanded) = remember { mutableStateOf(false) }
            val currentScreenTitle = currentScreen.title
            if (currentScreenTitle != MovieScreen.Login.title &&
                currentScreenTitle != MovieScreen.Register.title) {
                CenterAlignedTopAppBar(
                    title = {
                        if (currentScreenTitle == MovieScreen.Detail.title
                            || currentScreenTitle == MovieScreen.Comment.title) {
                            Text (
                                text = sharedMovieItem.title,
                                style = TextStyle(fontSize = 18.sp)
                            )
                        } else {
                            Text (
                                text = stringResource(currentScreenTitle),
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
                        else if (currentScreenTitle == MovieScreen.EditProfile.title) {
                            IconButton(onClick = { navController.navigate(MovieScreen.Profile.name)}) {
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
                                        navController.navigate(MovieScreen.AddComment.name)
                                    }
                                ) {
                                    Text("Add Comments")
                                }
                            }
                        }
                        if (currentScreenTitle == MovieScreen.Profile.title) {
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
                                        navController.navigate(MovieScreen.EditProfile.name)
                                    }
                                ) {
                                    Text("Edit")
                                }
                            }

                        }
                        if (currentScreenTitle == MovieScreen.EditProfile.title) {
                            IconButton(
                                onClick = {
                                    editProfileSetShowToast(true)
                                    if (editProfileUserName == "Hint: Enter user name"
                                        || editProfilePassword == "Hint: Enter password"
                                        || editProfileConfirmPassword == "Hint: Enter password"
                                        || editProfileEmail == "Hint: enter email"
                                        || editProfileGender == "No selection"
                                        || editProfileMobileNumber == "Hint: Enter mobile number"
                                        || editProfileGender == "Select Year of birth"){
                                        // Deny registering if required values not filled up
                                        editProfileSetToastMessage("An empty field was found")
                                    }
                                    else if (editProfilePassword != editProfileConfirmPassword) {
                                        editProfileSetToastMessage("Passwords do not match")
                                    }

                                    else {
                                        // TODO: Handle Register logic, write to UserProfile
                                        val updateVal = editProfileUpdates == "Yes"
                                        editProfileSetShowToast(false)
                                        MovieRaterApplication.instance.userProfile =
                                            UserProfile(editProfileUserName,
                                                editProfilePassword, editProfileEmail,
                                                editProfileGender, editProfileMobileNumber,
                                                updateVal, editProfileYearOfBirth)
                                        Log.d("User profile : ", ""+MovieRaterApplication.instance.userProfile?.userName)
                                        navController.navigate(MovieScreen.Profile.name)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Save"
                                )
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
                        setIndex = setMovieIndex,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(route = MovieScreen.Profile.name) {
                    ProfileScreen(
                        sharedAvatarInt = sharedAvatarInt
                    )
                }
                composable(route = MovieScreen.EditProfile.name) {
                    EditProfile(
                        sharedAvatarInt = sharedAvatarInt,
                        setSharedAvatarInt = setSharedAvatarInt,
                        userName = editProfileUserName,
                        setUserName = editProfileSetUserName,
                        password = editProfilePassword,
                        setPassword = editProfileSetPassword,
                        passwordVisible = editProfilePasswordVisible,
                        setPasswordVisible = editProfileSetPasswordVisible,
                        confirmPassword = editProfileConfirmPassword,
                        setConfirmPassword = editProfileSetConfirmPassword,
                        confirmPasswordVisible = editProfileConfirmPasswordVisible,
                        setConfirmPasswordVisible = editProfileSetConfirmPasswordVisible,
                        gender = editProfileGender,
                        setGender = editProfileSetGender,
                        updates = editProfileUpdates,
                        setUpdate = editProfileSetUpdate,
                        email = editProfileEmail,
                        setEmail = editProfileSetEmail,
                        yearOfBirth = editProfileYearOfBirth,
                        setYearOfBirth = editProfileSetYearOfBirth,
                        options = editProfileOptions,
                        showToast = editProfileShowToast,
                        toastMessage = editProfileToastMessage,
                        mobileNumber = editProfileMobileNumber,
                        setMobileNumber = editProfileSetMobileNumber

                    )
                }
                composable(
                    route = MovieScreen.Detail.name
                ){
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
                composable(route = MovieScreen.AddComment.name) {
                    AddCommentScreen(
                        sharedMovieIndex = movieIndex,
                        onClickSubmitComment = {
                            navController.navigate(MovieScreen.Detail.name)
                        }
                    )
                }
            }
        }
    }
}
