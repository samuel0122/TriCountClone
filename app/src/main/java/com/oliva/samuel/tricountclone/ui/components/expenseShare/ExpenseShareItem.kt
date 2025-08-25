package com.oliva.samuel.tricountclone.ui.components.expenseShare

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.utils.StringFormats

@Composable
fun ExpenseShareItem(
    modifier: Modifier = Modifier,
    participantName: String,
    amountOwned: Double,
    expenseCurrency: Currency
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = participantName
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = StringFormats.formatCurrency(
                value = amountOwned,
                currency = expenseCurrency
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseShareItemPreview() {
    ExpenseShareItem(
        participantName = "Samuel",
        amountOwned = 49.0,
        expenseCurrency = Currency.Euro
    )
}
