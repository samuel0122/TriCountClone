package com.oliva.samuel.tricountclone.ui.dialogs.tricount.addTricount

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.components.tricount.TricountForm

@Composable
fun AddTricountDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onTricountAdded: (TricountModel) -> Unit
) {
    if (show) {
        val viewModel: AddTricountViewModel = hiltViewModel()

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true
            )
        ) {
            Card {
                TricountForm(
                    tricountModel = viewModel.tricountModel.value,
                    userParticipant = viewModel.userParticipant.value,
                    participants = viewModel.participantsList,
                    onTricountModelChanged = viewModel::onTricountModelChanged,
                    onParticipantModelChanged = viewModel::onParticipantModelChanged,
                    onAddParticipant = viewModel::onAddParticipant,
                    onRemoveParticipant = viewModel::onRemoveParticipant,
                    onSubmitClick = { viewModel.onSubmit(onSubmitted = onTricountAdded) }
                )
            }
        }
    }
}
