package com.oliva.samuel.tricountclone.ui.navigation

sealed class TriCountCloneRoutes(val route: String) {
    data object SplashScreen : TriCountCloneRoutes("SplashScreen")
    data object MainScreen : TriCountCloneRoutes("MainScreen")

}
