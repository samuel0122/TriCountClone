package com.oliva.samuel.tricountclone.ui.components.tricount

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import java.time.Instant
import java.util.Date
import java.util.UUID

@Composable
fun TricountItem(
    modifier: Modifier = Modifier,
    tricountModel: TricountModel,
    onTricountSelected: (TricountModel) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tricountModel.icon,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = tricountModel.title
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
    Scaffold {
        TricountItem(
            modifier = Modifier.padding(it),
            tricountModel = TricountModel(
                id = UUID.randomUUID(),
                title = "Moros y Rafidash",
                icon = "🎡",
                currency = "€",
                createdBy = UUID.randomUUID(),
                createdAt = Date.from(Instant.now())
            )
        ) {
            Log.d("TricountItem", "TricountItem: $it")
        }
    }
}
