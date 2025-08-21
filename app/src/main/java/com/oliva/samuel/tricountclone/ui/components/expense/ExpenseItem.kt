package com.oliva.samuel.tricountclone.ui.components.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    expenseModel: ExpenseUiModel,
    onExpenseSelected: (ExpenseUiModel) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onExpenseSelected(expenseModel) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💶"
            )

            Column {
                Text(
                    text = expenseModel.title
                )

                Text(
                    text = "Paid by: ${expenseModel.paidBy}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = expenseModel.amount.toString()
            )
        }
    }
}

@Preview
@Composable
fun ExpenseItemPreview() {
    ExpenseItem(
        expenseModel = ExpenseModel.default().toUiModel(),
        onExpenseSelected = {}
    )
}
