package com.example.testing.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testing.ui.presentation.login.LoginScreen
import com.example.testing.ui.presentation.postsList.PostsListScreen
import com.example.testing.ui.presentation.register.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                navigateToPostsListScreen = { navController.navigate(Screen.PostsList.route) },
                navigateToRegisterScreen = { navController.navigate(Screen.Register.route) })

        }

        composable(route = Screen.Register.route) {
            RegisterScreen(navigateToLoginListScreen = { navController.navigate(Screen.Login.route) })
        }

        composable(route = Screen.PostsList.route) {
            PostsListScreen(
                navigateToLoginScreen = { navController.navigate(Screen.Login.route) },
                navigateToSinglePostScreen = { id -> navController.navigate(Screen.SinglePost.route + "/$id") }
            )
        }

        composable(route = Screen.SinglePost.route + "/{postId}", arguments = listOf(
            navArgument("postId") {
                type = NavType.IntType
                nullable = false
            }
        )) {

        }
    }
}