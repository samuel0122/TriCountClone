package com.oliva.samuel.tricountclone.ui.dialogs.tricount

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.components.tricount.TricountForm

@Composable
fun AddTricountDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onTricountAdded: (TricountModel) -> Unit
) {
    if (show) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true
            )
        ) {
            Card {
                TricountForm(
                    tricountModel = TricountModel.default(),
                    onSubmit = onTricountAdded
                )
            }
        }
    }
}
