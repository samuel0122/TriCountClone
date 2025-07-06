package com.oliva.samuel.tricountclone.ui.screens.tricountsScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.components.tricount.TricountsList
import com.oliva.samuel.tricountclone.ui.dialogs.tricount.AddTricountDialog
import com.oliva.samuel.tricountclone.utils.Resource

@Composable
fun TricountsScreen(
    navController: NavController,
    tricountsScreenViewModel: TricountsScreenViewModel
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val showAddTricountDialog by tricountsScreenViewModel.showAddTricountDialog.observeAsState(false)

    val uiState by produceState<Resource<List<TricountModel>>>(
        initialValue = Resource.Loading,
        key1 = lifecycle,
        key2 = tricountsScreenViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            tricountsScreenViewModel.tricountsList.collect {
                value = it
            }
        }
    }

    when (val tricountsList = uiState) {
        is Resource.Error -> {
            Text(
                text = tricountsList.message.orEmpty()
            )
        }

        Resource.Loading -> {
            CircularProgressIndicator()
        }

        is Resource.Success -> {
            TricountsScreenScaffold(
                navController = navController,
                tricountsScreenViewModel = tricountsScreenViewModel,
                tricountsList = tricountsList.data
            )
        }
    }

    AddTricountDialog(
        show = showAddTricountDialog,
        onDismiss = { tricountsScreenViewModel.onDismissAddTricountDialog() },
        onTricountAdded = { tricountsScreenViewModel.onTricountAdded(it) }
    )
}

@Composable
fun TricountsScreenScaffold(
    navController: NavController,
    tricountsScreenViewModel: TricountsScreenViewModel,
    tricountsList: List<TricountModel>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { tricountsScreenViewModel.onShowAddTricountDialogClick() }
            ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add task") }
        }
    ) { innerPadding ->
        TricountsList(
            modifier = Modifier.padding(innerPadding),
            tricountsList = tricountsList,
            onTricountSelected = {

            }
        )
    }
}
