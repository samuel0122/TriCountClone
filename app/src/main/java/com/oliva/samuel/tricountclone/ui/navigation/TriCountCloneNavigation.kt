package com.oliva.samuel.tricountclone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.oliva.samuel.tricountclone.ui.screens.SplashScreen
import com.oliva.samuel.tricountclone.ui.screens.mainScreen.MainScreen
import com.oliva.samuel.tricountclone.ui.screens.mainScreen.MainScreenViewModel
import com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen.TricountDetailScreen
import com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen.TricountDetailViewModel
import com.oliva.samuel.tricountclone.ui.screens.tricountsScreen.TricountsScreen
import com.oliva.samuel.tricountclone.ui.screens.tricountsScreen.TricountsScreenViewModel
import java.util.UUID

@Composable
fun TriCountCloneNavigation() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = TricountCloneDestinations.SplashScreen
    ) {
        composable<TricountCloneDestinations.SplashScreen> {
            SplashScreen {
                navigationController.navigate(TricountCloneDestinations.TricountsScreen)
            }
        }

        composable<TricountCloneDestinations.MainScreen> {
            val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()

            MainScreen(
                navController = navigationController,
                mainScreenViewModel = mainScreenViewModel
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
            val tricountId = UUID.fromString(tricoundDetails.tricountId)

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
