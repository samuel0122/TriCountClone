package com.oliva.samuel.tricountclone.ui.dialogs.tricount.addExpense

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import com.oliva.samuel.tricountclone.ui.components.expense.ExpenseForm
import java.util.UUID


@Composable
fun AddExpenseDialog(
    show: Boolean,
    tricountId: UUID,
    onDismiss: () -> Unit,
    onExpenseAdded: (ExpenseModel,List<ExpenseShareModel>) -> Unit
) {
    if (show) {
        val viewModel: AddExpenseViewModel = hiltViewModel()
        viewModel.loadTricount(tricountId)

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true
            )
        ) {
            Card {
                ExpenseForm(
                    expenseModel = viewModel.expenseModel.value,
                    participants = viewModel.participantsList,
                    expenseSharesList = viewModel.expenseSharesList,
                    expenseCurrency = viewModel.tricountInfo.value.currency,
                    onExpenseModelChanged = viewModel::onExpenseModelChanged,
                    onAddParticipantExpenseShare = viewModel::onAddParticipantExpenseShare,
                    onRemoveParticipantExpenseShare = viewModel::onRemoveParticipantExpenseShare,
                    onSubmitClick = { viewModel.onSubmitClick(onSubmitted = onExpenseAdded) }
                )
            }
        }
    }
}
