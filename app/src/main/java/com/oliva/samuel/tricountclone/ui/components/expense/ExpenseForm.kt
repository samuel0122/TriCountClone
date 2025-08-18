package com.oliva.samuel.tricountclone.ui.components.expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import com.oliva.samuel.tricountclone.ui.components.CapsuleDropdownMenu
import com.oliva.samuel.tricountclone.ui.components.CapsuleTextField
import com.oliva.samuel.tricountclone.ui.components.CurrencyTextField
import com.oliva.samuel.tricountclone.ui.components.CurrencyTextFieldType
import com.oliva.samuel.tricountclone.ui.components.expenseShare.EditParticipantsExpenseSharesList


@Composable
fun ExpenseForm(
    expenseModel: ExpenseModel,
    participants: List<ParticipantModel>,
    expenseSharesList: List<ExpenseShareModel>,
    expenseCurrency: Currency,
    onExpenseModelChanged: (ExpenseModel) -> Unit,
    onAddParticipantExpenseShare: (ParticipantModel) -> Unit,
    onRemoveParticipantExpenseShare: (ParticipantModel) -> Unit,
    onSubmitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Add Expense", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Title"
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CapsuleTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = expenseModel.title,
                onValueChange = { onExpenseModelChanged(expenseModel.copy(title = it)) },
                placeholder = { Text("E.g. Drinks") },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Amount"
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CurrencyTextField(
                value = expenseModel.amount,
                onValueChange = { newValue ->
                    onExpenseModelChanged(expenseModel.copy(amount = newValue))
                },
                currency = expenseCurrency,
                type = CurrencyTextFieldType.Capsule,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Paid By"
        )
        CapsuleDropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            items = participants.map { it.name },
            selectedItem = participants.find { it.id == expenseModel.paidBy }?.name.orEmpty(),
            onItemClick = { participantName ->
                participants.find { it.name == participantName }?.let {
                    onExpenseModelChanged(
                        expenseModel.copy(
                            paidBy = it.id
                        )
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Participants"
        )
        EditParticipantsExpenseSharesList(
            modifier = Modifier.fillMaxWidth(),
            participants = participants,
            expenseSharesList = expenseSharesList,
            expenseCurrency = expenseCurrency,
            onAddParticipantExpenseShare = onAddParticipantExpenseShare,
            onRemoveParticipantExpenseShare = onRemoveParticipantExpenseShare
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSubmitClick,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseFormPreview() {
    val participantsList = listOf(
        ParticipantModel.default().copy(name = "User"),
        ParticipantModel.default().copy(name = "Participant 1"),
        ParticipantModel.default().copy(name = "Participant 2")
    )
    ExpenseForm(
        expenseModel = ExpenseModel.default(),
        participants = participantsList,
        expenseSharesList = listOf(
            ExpenseShareModel.default().copy(participantId = participantsList.first().id)
        ),
        expenseCurrency = Currency.Euro,
        onExpenseModelChanged = {},
        onAddParticipantExpenseShare = {},
        onRemoveParticipantExpenseShare = {},
        onSubmitClick = {}
    )
}
