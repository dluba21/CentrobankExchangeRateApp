package com.example.centrobankrf.model

data class CurrencyResponse(
    val Date: String,
    val Valute: Map<String, Currency>
)