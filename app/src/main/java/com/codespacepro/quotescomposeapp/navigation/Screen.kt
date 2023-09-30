package com.codespacepro.quotescomposeapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{author}/{content}/{dateAdded}") {
        fun passData(author: String, content: String, dateAdded: String): String {
            return "detail/$author/$content/$dateAdded"
        }
    }
}
