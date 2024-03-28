package com.example.centrobankrf.repository

import android.content.SharedPreferences
import com.example.centrobankrf.model.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPrefRepository {
    fun saveCurrency(list: List<Currency>, key: String, sharedPreferences: SharedPreferences) {
        val gson = Gson()
        val json = gson.toJson(list)
        saveString(json, key, sharedPreferences)
    }

    fun loadCurrency(key: String, sharedPreferences: SharedPreferences): List<Currency> {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        val type = object : TypeToken<List<Currency>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveString(value: String, key: String, sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun loadString(key: String, sharedPreferences: SharedPreferences): String? {
        return sharedPreferences.getString(key, null)
    }
}