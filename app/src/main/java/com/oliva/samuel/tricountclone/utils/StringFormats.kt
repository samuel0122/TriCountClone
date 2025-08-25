package com.oliva.samuel.tricountclone.utils

import com.oliva.samuel.tricountclone.domain.model.Currency
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object StringFormats {

    fun formatCurrency(value: Double, currency: Currency): String {
        val formatter = java.text.NumberFormat.getCurrencyInstance().apply {
            this.currency = java.util.Currency.getInstance(currency.code)
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
        return formatter.format(value)
    }

    fun formatDateTime(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }

}
