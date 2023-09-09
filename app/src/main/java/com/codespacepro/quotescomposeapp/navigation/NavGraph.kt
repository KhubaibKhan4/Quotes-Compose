package com.codespacepro.quotescomposeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codespacepro.quotescomposeapp.screen.DetailScreen


//this will be implemented soon........
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            //this will be implemented soon........
        }
        composable(Screen.Detail.route) {
            DetailScreen()
        }
    }

}

//this will be implemented soon........