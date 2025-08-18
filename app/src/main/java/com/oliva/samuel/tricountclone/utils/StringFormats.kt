package com.oliva.samuel.tricountclone.utils

import com.oliva.samuel.tricountclone.domain.model.Currency

object StringFormats {

    fun formatCurrency(value: Double, currency: Currency): String {
        val formatter = java.text.NumberFormat.getCurrencyInstance().apply {
            this.currency = java.util.Currency.getInstance(currency.code)
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
        return formatter.format(value)
    }

}
