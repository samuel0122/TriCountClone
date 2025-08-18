package com.oliva.samuel.tricountclone.domain.model

enum class Currency(val symbol: String, val string: String, val code: String) {
    Euro("€", "Euro", "EUR"),
    BritishPound("£", "British Pound", "GBP"),
    USDollar("$", "US Dollar", "USD"),
    SwissFranc("Fr", "Swiss Franc", "CHF"),
    JapaneseYen("¥", "Japanese Yen", "JPY"),
    CanadianDollar("$", "Canadian Dollar", "CAD"),
    AustralianDollar("$", "Australian Dollar", "AUD"),
    ChineseYuan("¥", "Chinese Yuan", "CNY"),
    IndianRupee("₹", "Indian Rupee", "INR"),
    MexicanPeso("$", "Mexican Peso", "MXN"),
    BrazilianReal("R$", "Brazilian Real", "BRL"),
    RussianRuble("₽", "Russian Ruble", "RUB");

    companion object {
        fun fromSymbol(symbol: String): Currency =
            entries.find { it.symbol == symbol } ?: Euro

        fun fromString(symbol: String): Currency =
            entries.find { it.string == symbol } ?: Euro
    }
}
