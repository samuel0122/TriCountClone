package com.oliva.samuel.tricountclone.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.utils.StringFormats

@Composable
fun TextMoney(
    modifier: Modifier = Modifier,
    value: Double,
    currency: Currency
) {
    Text(
        text = StringFormats.formatCurrency(value, currency),
        modifier = modifier
    )
}
