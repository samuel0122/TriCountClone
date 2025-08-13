package com.oliva.samuel.tricountclone.domain.model

enum class Currency(val symbol: String, val string: String) {
    Euro("€", "Euro"),
    BritishPound("£", "British Pound"),
    USDollar("$", "US Dollar"),
    SwissFranc("Fr", "Swiss Franc");

    companion object {
        fun toString(currency: Currency): String =
            when (currency) {
                Euro -> "Euro"
                BritishPound -> "British Pound"
                USDollar -> "US Dollar"
                SwissFranc -> "Swiss Franc"
            }

        fun fromSymbol(symbol: String): Currency =
            entries.find { it.symbol == symbol } ?: Euro

        fun fromString(symbol: String): Currency =
            entries.find { it.string == symbol } ?: Euro

        fun toSymbol(currency: Currency): String =
            currency.symbol
    }
}
