package com.oliva.samuel.tricountclone.ui.widgets.tricount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel

@Composable
fun TricountsList(
    modifier: Modifier = Modifier,
    tricountsList: List<TricountUiModel>,
    onTricountSelected: (TricountUiModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tricountsList, key = { it.id }) { tricountItem ->
            TricountItem(
                tricountIcon = tricountItem.icon,
                tricountTitle = tricountItem.title,
                onItemClick = { onTricountSelected(tricountItem) }
            )
        }
    }
}
