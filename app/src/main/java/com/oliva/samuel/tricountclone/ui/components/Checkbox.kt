package com.oliva.samuel.tricountclone.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CircularCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    checkedColor: Color = CheckboxDefaults.colors().checkedBorderColor,
    uncheckedColor: Color = CheckboxDefaults.colors().uncheckedBorderColor,
) {
    Crossfade(
        targetState = checked,
        modifier = modifier,
        label = "CircularCheckChangeAnimation"
    ) { isChecked ->
        Icon(
            imageVector = if (isChecked) Icons.Outlined.CheckCircle else Icons.Outlined.Circle,
            contentDescription = null,
            tint = if (isChecked) checkedColor else uncheckedColor
        )
    }
}
