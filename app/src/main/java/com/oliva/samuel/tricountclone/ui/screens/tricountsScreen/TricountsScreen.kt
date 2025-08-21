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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.ui.components.tricount.TricountsList
import com.oliva.samuel.tricountclone.ui.dialogs.tricount.addTricount.AddTricountDialog
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import com.oliva.samuel.tricountclone.utils.Resource

@Composable
fun TricountsScreen(
    tricountsScreenViewModel: TricountsScreenViewModel,
    navigateToTricountDetail: (TricountId) -> Unit
) {
    val uiState by tricountsScreenViewModel.tricountsList.collectAsState()
    val showAddTricountDialog by tricountsScreenViewModel.showAddTricountDialog.observeAsState(false)

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
                tricountsList = tricountsList.data,
                onAddTricount = tricountsScreenViewModel::onShowAddTricountDialogClick,
                onTricountSelected = { navigateToTricountDetail(it.id) }
            )
        }
    }

    AddTricountDialog(
        show = showAddTricountDialog,
        onDismiss = tricountsScreenViewModel::onDismissAddTricountDialog,
        onTricountAdded = tricountsScreenViewModel::onTricountAdded
    )
}

@Composable
fun TricountsScreenScaffold(
    tricountsList: List<TricountUiModel>,
    onAddTricount: () -> Unit,
    onTricountSelected: (TricountUiModel) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTricount
            ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add task") }
        }
    ) { innerPadding ->
        TricountsList(
            modifier = Modifier.padding(innerPadding),
            tricountsList = tricountsList,
            onTricountSelected = onTricountSelected
        )
    }
}
