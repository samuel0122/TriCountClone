package com.oliva.samuel.tricountclone.ui.screens.expenseDetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.components.UiItemCard
import com.oliva.samuel.tricountclone.ui.widgets.expenseShare.ExpenseShareItem
import com.oliva.samuel.tricountclone.ui.widgets.expenseShare.ExpenseSharesList
import com.oliva.samuel.tricountclone.ui.model.ExpenseShareUiModel
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel
import com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen.TricountDetailScreenScaffold
import com.oliva.samuel.tricountclone.utils.Resource
import com.oliva.samuel.tricountclone.utils.StringFormats

@Composable
fun ExpenseDetailScreen(
    expenseDetailViewModel: ExpenseDetailViewModel
) {
    val uiState by expenseDetailViewModel.expenseDetail.collectAsState()

    when (val expenseDetailUiInfo = uiState) {
        is Resource.Error -> {
            Text(
                text = expenseDetailUiInfo.message.orEmpty()
            )
        }

        Resource.Loading -> {
            CircularProgressIndicator()
        }

        is Resource.Success -> {
            ExpenseDetailScreenScaffold(
                expense = expenseDetailUiInfo.data.expenseUiModel,
                paidBy = expenseDetailUiInfo.data.paidBy,
                expenseShares = expenseDetailUiInfo.data.expenseShares,
                expenseSharesParticipants = expenseDetailUiInfo.data.expenseSharesParticipants
            )
        }
    }
}

@Composable
fun ExpenseDetailScreenScaffold(
    expense: ExpenseUiModel,
    paidBy: ParticipantUiModel,
    expenseShares: List<ExpenseShareUiModel>,
    expenseSharesParticipants: Map<ParticipantId, ParticipantUiModel>
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "💶",
                fontSize = 30.sp
            )

            Text(
                text = expense.title
            )

            Text(
                text = StringFormats.formatDateTime(expense.createdAt)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Paid by"
            )

            UiItemCard {
                ExpenseShareItem(
                    participantName = paidBy.name,
                    amountOwned = expense.amount,
                    expenseCurrency = Currency.Euro,
                    amountOwnedTextColor = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "For ${expenseSharesParticipants.size} participants:"
            )

            ExpenseSharesList(
                expenseSharesList = expenseShares,
                expenseSharesParticipants = expenseSharesParticipants,
                expenseCurrency = Currency.Euro
            )
        }
    }
}

@Preview
@Composable
fun ExpenseDetailScrenPreview() {
    TricountDetailScreenScaffold(
        tricount = TricountModel.default().copy(
            title = "Tricount Example",
            icon = "🏠"
        ).toUiModel(),
        participants = emptyMap(),
        expenses = emptyList(),
        onAddExpense = {},
        onExpenseSelected = {},
        navigateBack = {}
    )
}
