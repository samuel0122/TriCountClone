package com.oliva.samuel.tricountclone.ui.components.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel

@Composable
fun ExpensesList(
    modifier: Modifier = Modifier,
    expensesList: List<ExpenseUiModel>,
    participants: Map<ParticipantId, ParticipantUiModel>,
    onExpenseSelected: (ExpenseUiModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(expensesList, key = { it.id }) { expenseItem ->
            ExpenseItem(
                expenseTitle = expenseItem.title,
                expenseAmount = expenseItem.amount,
                participantName = participants[expenseItem.paidBy]?.name.orEmpty(),
                onItemClick = { onExpenseSelected(expenseItem) }
            )
        }
    }
}
