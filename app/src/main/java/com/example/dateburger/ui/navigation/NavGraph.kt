package com.example.dateburger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dateburger.ui.screen.portada.PortadaScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "portada"
    ) {
        composable("portada") {
            PortadaScreen()
        }
    }
}