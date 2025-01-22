package com.example.assignment2.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.assignment2.MyViewModel
import com.example.assignment2.data.User
import com.example.assignment2.ui.components.DetailScreen
import com.example.assignment2.ui.components.FavDetailScreen
import com.example.assignment2.ui.components.FavouriteScreen
import com.example.assignment2.ui.components.LandingScreen
import com.example.assignment2.ui.components.LoginScreen
import com.example.assignment2.ui.components.ProfileScreen
import com.example.assignment2.ui.components.RegisterScreen
import com.example.assignment2.ui.components.ReviewScreen
import com.example.assignment2.ui.components.SearchScreen
import com.example.assignment2.ui.components.SimilarScreen
import com.example.assignment2.utils.AppBarState
import com.example.assignment2.utils.BaseAppBar
import com.example.assignment2.utils.SwitchScreen

@Composable
fun MainScreen(viewModel: MyViewModel = viewModel(factory = MyViewModel.factory)) {
    val navController = rememberNavController()
    val backStackEntryState = navController.currentBackStackEntryAsState()
    val backStackEntry  = backStackEntryState.value

    var (expanded, setExpanded) = remember { mutableStateOf(false) }

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
        NavHost(navController = navController, startDestination = SwitchScreen.Login.name) {
            composable(SwitchScreen.Login.name) {
                appBarState.value = AppBarState(title = SwitchScreen.Login.name, navigationIcon = {}, actions = {})
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
                appBarState.value = AppBarState(title = SwitchScreen.Landing.name,
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(SwitchScreen.Login.name)
                            viewModel.updateCurrentUser(User(0, "", "", ""))
                        }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { setExpanded(true)}) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "More",
                                tint = Color.White
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { setExpanded(false)}
                        ) {
                            TextButton(
                                onClick = {
                                    navController.navigate(SwitchScreen.Profile.name)
                                }
                            ) {
                                Text("View Profile")
                            }
                            TextButton(
                                onClick = {
                                    navController.navigate(SwitchScreen.Favourite.name)
                                }
                            ) {
                                Text("View Favourite")
                            }
                        }
                    })
                LandingScreen(viewModel, innerPadding, onClickMovieItem = {
                    navController.navigate(SwitchScreen.Detail.name)
                }, onTriggerSearch = {
                    navController.navigate(SwitchScreen.Search.name)
                } )
            }
            composable(SwitchScreen.Register.name) {
                appBarState.value = AppBarState( title = SwitchScreen.Register.name,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(SwitchScreen.Login.name)}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }, actions = {}
                )
                RegisterScreen(
                    viewModel,
                    { navController.navigate(SwitchScreen.Login.name) }
                )
            }
            composable(SwitchScreen.Profile.name) {
                appBarState.value = AppBarState(title= SwitchScreen.Profile.name, navigationIcon = {
                    IconButton(onClick = { navController.navigate(SwitchScreen.Landing.name)}) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                })
                ProfileScreen(viewModel)
            }
            composable(SwitchScreen.Detail.name) {
                appBarState.value = AppBarState(title= SwitchScreen.Detail.name,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(SwitchScreen.Landing.name)}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }, actions = {})
                DetailScreen(
                    viewModel,
                    onNavigateReview = {
                        navController.navigate(SwitchScreen.Review.name)
                    },
                    onNavigateSimilar = {
                        navController.navigate(SwitchScreen.Similar.name)
                    },
                    onFavouriteAction = {
                        navController.navigate(SwitchScreen.Favourite.name)
                    }
                )
            }
            composable(SwitchScreen.Review.name) {
                appBarState.value = AppBarState(title= SwitchScreen.Review.name,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(SwitchScreen.Detail.name)}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }, actions = {})
                ReviewScreen(viewModel)
            }
            composable(SwitchScreen.Search.name) {
                appBarState.value = AppBarState(title= SwitchScreen.Search.name,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(SwitchScreen.Landing.name)}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }, actions = {})
                SearchScreen(viewModel, innerPadding)
            }
            // TODO: Similar, search screen, favourite movie list
            composable(SwitchScreen.Similar.name) {
                appBarState.value = AppBarState(title= SwitchScreen.Similar.name,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(SwitchScreen.Detail.name)}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }, actions = {})
                SimilarScreen(viewModel, innerPadding, onClickMovieItem = {
                    navController.navigate(SwitchScreen.Detail.name)
                })
            }
            composable(SwitchScreen.Favourite.name) {
                appBarState.value = AppBarState(title= SwitchScreen.Favourite.name,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(SwitchScreen.Landing.name)}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }, actions = {})
                FavouriteScreen(viewModel, innerPadding, onClickMovieItem = {navController.navigate(SwitchScreen.FavouriteDetail.name)})
            }
            composable(SwitchScreen.FavouriteDetail.name) {
                appBarState.value = AppBarState(title= SwitchScreen.FavouriteDetail.name,
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(SwitchScreen.Favourite.name)}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }, actions = {})
                FavDetailScreen(viewModel, onDelete = {navController.navigate(SwitchScreen.Favourite.name)})
            }

        }
    }
}