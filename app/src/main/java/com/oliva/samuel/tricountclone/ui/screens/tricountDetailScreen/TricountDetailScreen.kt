package com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.dialogs.tricount.addExpense.AddExpenseDialog
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import com.oliva.samuel.tricountclone.ui.widgets.expense.ExpensesList
import com.oliva.samuel.tricountclone.utils.Resource

@Composable
fun TricountDetailScreen(
    tricountDetailViewModel: TricountDetailViewModel,
    navigateToExpenseDetail: (ExpenseId) -> Unit
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
                tricount = tricountsInfo.data.tricount,
                participants = tricountsInfo.data.participants,
                expenses = tricountsInfo.data.expenses,
                onAddExpense = tricountDetailViewModel::onShowAddExpenseDialogClick,
                onExpenseSelected = { navigateToExpenseDetail(it.id) }
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
    tricount: TricountUiModel,
    participants: Map<ParticipantId, ParticipantUiModel>,
    expenses: List<ExpenseUiModel>,
    onAddExpense: () -> Unit,
    onExpenseSelected: (ExpenseUiModel) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = tricount.icon,
                fontSize = 30.sp
            )

            Text(
                text = tricount.title
            )

            if (expenses.isNotEmpty()) {
                ExpensesList(
                    expensesList = expenses,
                    participants = participants,
                    onExpenseSelected = onExpenseSelected
                )
            } else {

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nothing here for now. Tap + to add an expense!"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TricountDetailScrenPreview() {
    TricountDetailScreenScaffold(
        tricount = TricountModel.default().copy(
            title = "Tricount Example",
            icon = "🏠"
        ).toUiModel(),
        participants = emptyMap(),
        expenses = emptyList(),
        onAddExpense = {},
        onExpenseSelected = {}
    )
}
