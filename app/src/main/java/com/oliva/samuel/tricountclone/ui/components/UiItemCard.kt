package com.oliva.samuel.tricountclone.ui.components

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun UiItemCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.DarkGray.copy(alpha = 0.1f),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        content()
    }
}
