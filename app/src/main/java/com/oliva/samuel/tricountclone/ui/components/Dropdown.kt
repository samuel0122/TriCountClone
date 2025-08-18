package com.oliva.samuel.tricountclone.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.model.Currency


@Composable
fun CapsuleDropdownMenu(
    modifier: Modifier = Modifier,
    title: String? = null,
    items: List<String>,
    selectedItem: String,
    onDismiss: () -> Unit = {},
    onItemClick: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                //.clip(RoundedCornerShape(10.dp))
                .clickable { isExpanded = true }
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                title?.let {
                    Text(
                        text = title
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedItem
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }

                    DropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = {
                            onDismiss()
                            isExpanded = false
                        }
                    ) {
                        Column {
                            items.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        onItemClick(it)
                                        isExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuPreview() {
    var selectedText by remember { mutableStateOf(Currency.Euro.string) }
    CapsuleDropdownMenu(
        items = Currency.entries.map { it.string },
        title = "Currency",
        selectedItem = selectedText,
        onDismiss = {},
        onItemClick = { selectedText = it }
    )
}
