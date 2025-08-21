package com.oliva.samuel.tricountclone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.ui.screens.mainScreen.MainScreen
import com.oliva.samuel.tricountclone.ui.screens.mainScreen.MainScreenViewModel
import com.oliva.samuel.tricountclone.ui.screens.splashScreen.SplashScreen
import com.oliva.samuel.tricountclone.ui.screens.splashScreen.SplashViewModel
import com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen.TricountDetailScreen
import com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen.TricountDetailViewModel
import com.oliva.samuel.tricountclone.ui.screens.tricountsScreen.TricountsScreen
import com.oliva.samuel.tricountclone.ui.screens.tricountsScreen.TricountsScreenViewModel

@Composable
fun TriCountCloneNavigation() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = TricountCloneDestinations.SplashScreen
    ) {
        composable<TricountCloneDestinations.SplashScreen> {
            val splashViewModel = hiltViewModel<SplashViewModel>()

            SplashScreen(
                splashViewModel = splashViewModel,
                navigateToTricounts = {
                    navigationController.navigate(TricountCloneDestinations.TricountsScreen) {
                        popUpTo(TricountCloneDestinations.SplashScreen) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<TricountCloneDestinations.MainScreen> {
            val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()

            MainScreen(
                mainScreenViewModel = mainScreenViewModel,
                navigateToTricountsScreen = {
                    navigationController.navigate(TricountCloneDestinations.TricountsScreen)
                }
            )
        }

        composable<TricountCloneDestinations.TricountsScreen> {
            val tricountsScreenViewModel = hiltViewModel<TricountsScreenViewModel>()

            TricountsScreen(
                tricountsScreenViewModel = tricountsScreenViewModel,
                navigateToTricountDetail = { tricountId ->
                    navigationController.navigate(
                        TricountCloneDestinations.TricountDetailScreen(
                            tricountId.toString()
                        )
                    )
                }
            )
        }

        composable<TricountCloneDestinations.TricountDetailScreen> { backStackEntry ->
            val tricoundDetails =
                backStackEntry.toRoute<TricountCloneDestinations.TricountDetailScreen>()
            val tricountId = UserId.fromString(tricoundDetails.tricountId)

            val tricountDetailViewModel = hiltViewModel<TricountDetailViewModel>()

            LaunchedEffect(key1 = tricountId) {
                tricountDetailViewModel.loadTricount(tricountId)
            }

            TricountDetailScreen(
                tricountDetailViewModel = tricountDetailViewModel
            )
        }
    }
}
