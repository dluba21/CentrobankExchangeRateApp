package com.example.centrobankrf.util
import android.icu.text.SimpleDateFormat
import com.example.centrobankrf.R


object DateHelper {
    private const val errorDateTime = "Ошибка: дата и время отсутствуют"
    fun getFormattedDate(date: String): String {
        val formatterInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        val formatterOutput = SimpleDateFormat("dd.MM.yyyy HH:mm:ssXXX")
        try {
            val inputDate = formatterInput.parse(date)
            return formatterOutput.format(inputDate)
        }
        catch (e: Throwable) {
            return errorDateTime
        }
    }
}