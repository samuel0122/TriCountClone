package com.oliva.samuel.tricountclone.ui.widgets.tricount

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oliva.samuel.tricountclone.ui.components.UiItemCard

@Composable
fun TricountItem(
    modifier: Modifier = Modifier,
    tricountIcon: String,
    tricountTitle: String,
    onItemClick: (() -> Unit)? = null
) {
    UiItemCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    onItemClick?.let {
                        Modifier.clickable { onItemClick() }
                    } ?: Modifier
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tricountIcon,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = tricountTitle
            )

            Spacer(modifier = Modifier.weight(1f))

            Badge {
                Text(text = "2")
            }

            Spacer(modifier = Modifier.width(5.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TricountItemPreview() {
    TricountItem(
        tricountIcon = "🎡",
        tricountTitle = "Moros y Rafidash"
    )
}
