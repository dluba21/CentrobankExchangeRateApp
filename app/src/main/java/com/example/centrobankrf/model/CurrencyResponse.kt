package com.example.centrobankrf.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("Timestamp") val timestamp: String,
    @SerializedName("Valute") val valute: Map<String, Currency>
)