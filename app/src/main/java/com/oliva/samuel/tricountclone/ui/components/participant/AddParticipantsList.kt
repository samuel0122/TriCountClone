package com.oliva.samuel.tricountclone.ui.components.participant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import com.oliva.samuel.tricountclone.ui.components.BorderlessTextField

@Composable
fun AddParticipantsList(
    modifier: Modifier = Modifier,
    userParticipant: ParticipantModel,
    participants: List<ParticipantModel>,
    onParticipantModelChanged: (ParticipantModel) -> Unit,
    onAddParticipant: () -> Unit,
    onRemoveParticipant: (ParticipantModel) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        LazyColumn {
            item {
                BorderlessTextField(
                    value = userParticipant.name,
                    onValueChange = { onParticipantModelChanged(userParticipant.copy(name = it)) },
                    placeholder = { Text("Your Name") },
                    singleLine = true,
                    maxLines = 1
                )
            }

            items(participants, key = { it.id }) { participant ->
                BorderlessTextField(
                    value = participant.name,
                    onValueChange = { onParticipantModelChanged(participant.copy(name = it)) },
                    placeholder = { Text("Participant Name") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete participant",
                            modifier = Modifier
                                .clickable { onRemoveParticipant(participant) }
                        )
                    },
                    singleLine = true,
                    maxLines = 1
                )
            }

            item {
                TextButton(
                    onClick = onAddParticipant
                ) {
                    Text("Add Participant")
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddParticipantsListPreview() {
    Scaffold { innerPadding ->
        AddParticipantsList(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            userParticipant = ParticipantModel.default()
                .copy(name = "Samuel"),
            participants = listOf(
                ParticipantModel.default()
                    .copy(name = "Jose"),
                ParticipantModel.default()
            ),
            onParticipantModelChanged = {},
            onAddParticipant = {},
            onRemoveParticipant = {}
        )
    }
}
