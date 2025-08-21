package com.oliva.samuel.tricountclone.ui.components.tricount

import android.util.Log
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
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import java.time.Instant
import java.util.Date

@Composable
fun TricountItem(
    modifier: Modifier = Modifier,
    tricountModel: TricountUiModel,
    onTricountSelected: (TricountUiModel) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onTricountSelected(tricountModel) }
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
                id = TricountId.randomUUID(),
                title = "Moros y Rafidash",
                icon = "🎡",
                currency = Currency.Euro,
                createdBy = UserId.randomUUID(),
                createdAt = Date.from(Instant.now())
            ).toUiModel()
        ) {
            Log.d("TricountUiModel", "TricountUiModel: $it")
        }
    }
}
