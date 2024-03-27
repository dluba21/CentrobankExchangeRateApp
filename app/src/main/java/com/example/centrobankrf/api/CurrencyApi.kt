package com.example.centrobankrf.api

import com.example.centrobankrf.model.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyApi {
    @GET("daily_json.js")
    fun getCurrencies(): Call<CurrencyResponse>
}