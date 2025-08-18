package com.oliva.samuel.tricountclone.ui.components.expenseShare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel

@Composable
fun EditParticipantsExpenseSharesList(
    modifier: Modifier = Modifier,
    participants: List<ParticipantModel>,
    expenseSharesList: List<ExpenseShareModel>,
    expenseCurrency: Currency,
    onAddParticipantExpenseShare: (ParticipantModel) -> Unit,
    onRemoveParticipantExpenseShare: (ParticipantModel) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            itemsIndexed(participants, key = { _, item -> item.id }) { index, participant ->
                val expenseShare = expenseSharesList.find { it.participantId == participant.id }

                ParticipantExpenseShareItem(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 18.dp),
                    participantModel = participant,
                    expenseShareModel = expenseShare,
                    expenseCurrency = expenseCurrency,
                    onParticipantExpenseShareSelected = {
                        expenseShare?.let {
                            onRemoveParticipantExpenseShare(participant)
                        } ?: run {
                            onAddParticipantExpenseShare(participant)
                        }
                    }
                )

                if (index < participants.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                }
            }
        }
    }
}
