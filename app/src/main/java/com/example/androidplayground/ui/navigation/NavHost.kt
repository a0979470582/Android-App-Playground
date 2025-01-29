package com.example.androidplayground.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidplayground.ui.brush.BrushScreen
import com.example.androidplayground.ui.home.HomeScreen

@Composable
fun AndroidPlaygroundNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Home.route, modifier = modifier) {
        composable(Home.route) {
            HomeScreen(onClickBrush = { navController.navigate(Brush.route) })
        }
        composable(Brush.route) {
            BrushScreen()
        }
    }
}
