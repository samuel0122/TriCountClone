package com.oliva.samuel.tricountclone.ui.components.tricount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.model.TricountModel

@Composable
fun TricountForm(
    tricountModel: TricountModel,
    onSubmit: (TricountModel) -> Unit
) {
    var title by rememberSaveable { mutableStateOf(tricountModel.title) }
    var icon by rememberSaveable { mutableStateOf(tricountModel.icon) }
    var currency by rememberSaveable { mutableStateOf(tricountModel.currency) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Crear Tricount", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = icon,
            onValueChange = { icon = it },
            label = { Text("Icono (emoji)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = currency,
            onValueChange = { currency = it },
            label = { Text("Moneda (símbolo)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSubmit(
                    tricountModel.copy(
                        title = title,
                        icon = icon,
                        currency = currency
                    )
                )
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Crear")
        }
    }
}
