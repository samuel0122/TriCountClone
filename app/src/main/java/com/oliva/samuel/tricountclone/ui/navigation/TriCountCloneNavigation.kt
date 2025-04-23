package com.oliva.samuel.tricountclone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oliva.samuel.tricountclone.ui.screens.SplashScreen
import com.oliva.samuel.tricountclone.ui.screens.mainScreen.MainScreen
import com.oliva.samuel.tricountclone.ui.screens.mainScreen.MainScreenViewModel

@Composable
fun TriCountCloneNavigation() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = TriCountCloneRoutes.SplashScreen.route
    ) {
        composable(TriCountCloneRoutes.SplashScreen.route) {
            SplashScreen(
                navController = navigationController
            )
        }

        composable(TriCountCloneRoutes.MainScreen.route) {
            val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()

            MainScreen(
                navController = navigationController,
                mainScreenViewModel = mainScreenViewModel
            )
        }
    }
}
