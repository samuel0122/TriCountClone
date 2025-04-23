package com.oliva.samuel.tricountclone.ui.screens.mainScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel
) {
    Scaffold { innerPadding ->
        Text(
            text = "Main Screen",
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}
