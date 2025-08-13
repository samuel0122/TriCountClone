package com.oliva.samuel.tricountclone.ui.navigation

import kotlinx.serialization.Serializable

object TricountCloneDestinations {

    @Serializable
    object SplashScreen

    @Serializable
    object MainScreen

    @Serializable
    object TricountsScreen

    @Serializable
    data class TricountDetailScreen(val tricountId: String)
}
