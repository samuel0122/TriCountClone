package com.oliva.samuel.tricountclone.ui.components.expenseShare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.ui.components.UiItemCard
import com.oliva.samuel.tricountclone.ui.model.ExpenseShareUiModel
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel

@Composable
fun ExpenseSharesList(
    modifier: Modifier = Modifier,
    expenseSharesList: List<ExpenseShareUiModel>,
    expenseSharesParticipants: Map<ParticipantId, ParticipantUiModel>,
    expenseCurrency: Currency
) {
    UiItemCard(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            itemsIndexed(
                expenseSharesList, key = { _, item -> item.participantId }
            ) { index, expenseShare ->

                ExpenseShareItem(
                    participantName = expenseSharesParticipants[expenseShare.participantId]?.name.orEmpty(),
                    amountOwned = expenseShare.amountOwed,
                    expenseCurrency = expenseCurrency
                )

                if (index < expenseSharesList.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                }
            }
        }
    }
}
