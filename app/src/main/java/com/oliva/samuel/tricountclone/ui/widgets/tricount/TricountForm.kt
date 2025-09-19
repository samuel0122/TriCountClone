package com.oliva.samuel.tricountclone.ui.widgets.tricount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.components.CapsuleDropdownMenu
import com.oliva.samuel.tricountclone.ui.components.CapsuleTextField
import com.oliva.samuel.tricountclone.ui.components.EmojiSelector
import com.oliva.samuel.tricountclone.ui.widgets.participant.AddParticipantsList
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel

@Composable
fun TricountForm(
    tricountModel: TricountUiModel,
    userParticipant: ParticipantUiModel,
    participants: List<ParticipantUiModel>,
    onTricountModelChanged: (TricountUiModel) -> Unit,
    onParticipantModelChanged: (ParticipantUiModel) -> Unit,
    onAddParticipant: () -> Unit,
    onRemoveParticipant: (ParticipantUiModel) -> Unit,
    onSubmitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Crear Tricount", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Title"
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            EmojiSelector(
                emoji = tricountModel.icon,
                onEmojiChange = { onTricountModelChanged(tricountModel.copy(icon = it)) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            CapsuleTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = tricountModel.title,
                onValueChange = { onTricountModelChanged(tricountModel.copy(title = it)) },
                placeholder = { Text("E.g. City Trip") },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Options"
        )
        CapsuleDropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            title = "Currency",
            items = Currency.entries.map { it.string },
            selectedItem = tricountModel.currency.string,
            onItemClick = {
                onTricountModelChanged(tricountModel.copy(currency = Currency.fromString(it)))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Participants"
        )
        AddParticipantsList(
            modifier = Modifier.fillMaxWidth(),
            participants = participants,
            userParticipant = userParticipant,
            onParticipantModelChanged = onParticipantModelChanged,
            onAddParticipant = onAddParticipant,
            onRemoveParticipant = onRemoveParticipant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSubmitClick,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Crear")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TricountFormPreview() {
    TricountForm(
        tricountModel = TricountModel.default().toUiModel(),
        userParticipant = ParticipantModel.default().toUiModel(),
        participants = emptyList(),
        onTricountModelChanged = {},
        onParticipantModelChanged = {},
        onAddParticipant = {},
        onRemoveParticipant = {},
        onSubmitClick = {}
    )
}
