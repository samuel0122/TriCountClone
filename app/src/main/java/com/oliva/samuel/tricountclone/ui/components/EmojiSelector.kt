package com.oliva.samuel.tricountclone.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmojiSelector(
    modifier: Modifier = Modifier,
    emoji: String,
    onEmojiChange: (String) -> Unit
) {
    Text(
        text = emoji,
        modifier = modifier
            .clickable {
                if (emoji == "😃")
                    onEmojiChange("😄")
                else
                    onEmojiChange("😃")
            }
    )
}
