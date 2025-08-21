package com.oliva.samuel.tricountclone.ui.components.participant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import com.oliva.samuel.tricountclone.ui.components.BorderlessTextField
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel

@Composable
fun AddParticipantsList(
    modifier: Modifier = Modifier,
    userParticipant: ParticipantUiModel,
    participants: List<ParticipantUiModel>,
    onParticipantModelChanged: (ParticipantUiModel) -> Unit,
    onAddParticipant: () -> Unit,
    onRemoveParticipant: (ParticipantUiModel) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            item {
                BorderlessTextField(
                    value = userParticipant.name,
                    onValueChange = { onParticipantModelChanged(userParticipant.copy(name = it)) },
                    placeholder = { Text("Your Name") },
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(25, 25, 25, 25))
                                .background(Color.Cyan)
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                text = "Me"
                            )
                        }
                    },
                    singleLine = true,
                    maxLines = 1
                )
            }

            items(participants, key = { it.id }) { participant ->
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )

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
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )

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
                .copy(name = "Samuel").toUiModel(),
            participants = listOf(
                ParticipantModel.default()
                    .copy(name = "Jose").toUiModel(),
                ParticipantModel.default().toUiModel()
            ),
            onParticipantModelChanged = {},
            onAddParticipant = {},
            onRemoveParticipant = {}
        )
    }
}
