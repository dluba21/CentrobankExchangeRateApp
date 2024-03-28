package com.example.centrobankrf.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("PreviousDate") val timestamp: String,
    @SerializedName("Valute") val valute: Map<String, Currency>
)