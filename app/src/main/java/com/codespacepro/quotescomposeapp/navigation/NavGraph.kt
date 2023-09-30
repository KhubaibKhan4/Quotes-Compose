package com.codespacepro.quotescomposeapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codespacepro.quotescomposeapp.screen.DetailScreen
import com.codespacepro.quotescomposeapp.screen.HomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Detail.route,
            arguments = listOf(
                navArgument("author") {
                    type = NavType.StringType
                },
                navArgument("content") {
                    type = NavType.StringType
                },
                navArgument("dateAdded") {
                    type = NavType.StringType
                }
            )
        ) {
            val author = it.arguments?.getString("author")
            val content = it.arguments?.getString("content")
            val dateAdded = it.arguments?.getString("dateAdded")
            Log.d("Detail", "SetupNavGraph: $author \n $content \n $dateAdded ")
            DetailScreen(navController, author, content, dateAdded)
        }
    }
}