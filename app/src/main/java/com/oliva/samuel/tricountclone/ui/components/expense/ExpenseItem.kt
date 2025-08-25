package com.oliva.samuel.tricountclone.ui.components.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.ui.components.UiItemCard

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    expenseTitle: String,
    expenseAmount: Double,
    participantName: String,
    onItemClick: (() -> Unit)? = null
) {
    UiItemCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    onItemClick?.let {
                        Modifier.clickable { onItemClick() }
                    } ?: Modifier
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💶"
            )

            Column {
                Text(
                    text = expenseTitle
                )

                Text(
                    text = "Paid by: $participantName",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = expenseAmount.toString()
            )
        }
    }
}

@Preview
@Composable
fun ExpenseItemPreview() {
    ExpenseItem(
        expenseTitle = "Expense",
        expenseAmount = 2.21,
        participantName = "Participant"
    )
}
