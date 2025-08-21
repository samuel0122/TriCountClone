package com.oliva.samuel.tricountclone.ui.components.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel

@Composable
fun ExpensesList(
    modifier: Modifier = Modifier,
    expensesList: List<ExpenseUiModel>,
    onExpenseSelected: (ExpenseUiModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(expensesList, key = { it.id }) { expenseItem ->
            ExpenseItem(
                expenseModel = expenseItem,
                onExpenseSelected = onExpenseSelected
            )
        }
    }
}
