package com.example.testing.ui.navigation

sealed class Screen(    val route: String){
    object Login: Screen("login")
    object Register: Screen("register")
    object PostsList: Screen("posts_list")
    object SinglePost: Screen("single_post")
}