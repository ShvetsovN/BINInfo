package com.example.bininfo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bininfo.ui.screen.HistoryScreen
import com.example.bininfo.ui.screen.HomeScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("historyScreen") {
            HistoryScreen(navController = navController)
        }
    }
}