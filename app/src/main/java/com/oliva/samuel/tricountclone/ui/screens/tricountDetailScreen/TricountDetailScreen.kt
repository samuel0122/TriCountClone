package com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.domain.model.TricountWithParticipantsAndExpensesModel
import com.oliva.samuel.tricountclone.ui.components.expense.ExpensesList
import com.oliva.samuel.tricountclone.ui.dialogs.tricount.addExpense.AddExpenseDialog
import com.oliva.samuel.tricountclone.utils.Resource

@Composable
fun TricountDetailScreen(
    tricountDetailViewModel: TricountDetailViewModel
) {
    val uiState by tricountDetailViewModel.tricount.collectAsState()
    val showAddExpenseDialog by tricountDetailViewModel.showAddExpenseDialog.observeAsState(false)

    when (val tricountsInfo = uiState) {
        is Resource.Error -> {
            Text(
                text = tricountsInfo.message.orEmpty()
            )
        }

        Resource.Loading -> {
            CircularProgressIndicator()
        }

        is Resource.Success -> {
            TricountDetailScreenScaffold(
                tricountWithParticipantsAndExpensesModel = tricountsInfo.data,
                onAddExpense = tricountDetailViewModel::onShowAddExpenseDialogClick,
                onExpenseSelected = { }
            )

            AddExpenseDialog(
                show = showAddExpenseDialog,
                tricountId = tricountsInfo.data.tricount.id,
                onDismiss = tricountDetailViewModel::onDismissAddExpenseDialog,
                onExpenseAdded = tricountDetailViewModel::onExpenseAdded
            )
        }
    }
}

@Composable
fun TricountDetailScreenScaffold(
    tricountWithParticipantsAndExpensesModel: TricountWithParticipantsAndExpensesModel,
    onAddExpense: () -> Unit,
    onExpenseSelected: (ExpenseModel) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpense
            ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add task") }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tricountWithParticipantsAndExpensesModel.tricount.title,
                modifier = Modifier.padding(innerPadding)
            )

            Text(
                text = tricountWithParticipantsAndExpensesModel.tricount.icon,
                modifier = Modifier.padding(innerPadding)
            )

            ExpensesList(
                expensesList = tricountWithParticipantsAndExpensesModel.expenses,
                onExpenseSelected = onExpenseSelected
            )
        }
    }
}

@Preview
@Composable
fun TricountDetailScrenPreview() {
    TricountDetailScreenScaffold(
        tricountWithParticipantsAndExpensesModel = TricountWithParticipantsAndExpensesModel(
            tricount = TricountModel.default().copy(
                title = "Tricount Example",
                icon = "🏠"
            ),
            participants = emptyList(),
            expenses = emptyList()
        ),
        onAddExpense = {},
        onExpenseSelected = {}
    )
}
