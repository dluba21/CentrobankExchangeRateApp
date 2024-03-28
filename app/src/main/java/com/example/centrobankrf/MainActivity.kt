package com.example.centrobankrf

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.centrobankrf.adapter.CurrencyAdapter
import com.example.centrobankrf.api.RetrofitClient
import com.example.centrobankrf.model.CurrencyResponse
import com.example.centrobankrf.repository.SharedPrefRepository
import com.example.centrobankrf.util.DateHelper.getFormattedDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_name), MODE_PRIVATE)
        recyclerView = findViewById(R.id.main_recycler)
        progressBar = findViewById(R.id.main_progress)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            updateData()
            displayData()
            handler.postDelayed(runnable, 30000)
        }

        handler.post(runnable)
    }

    private fun updateData() {
        progressBar.visibility = ProgressBar.VISIBLE
        RetrofitClient.instance.getCurrencies().enqueue(
            object : Callback<CurrencyResponse> {
                override fun onResponse(
                    call: Call<CurrencyResponse>,
                    response: Response<CurrencyResponse>
                ) {
                    progressBar.visibility = ProgressBar.GONE
                    if (response.isSuccessful) {
                        val currencies = response.body()?.valute?.values?.toList()

                        SharedPrefRepository.saveCurrency(currencies ?: emptyList(), "currencies", sharedPreferences)
                        SharedPrefRepository.saveString(response.body()?.timestamp ?: "", "lastUpdate", sharedPreferences)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Ошибка при загрузке данных",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                    progressBar.visibility = ProgressBar.GONE
                    Toast.makeText(this@MainActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun displayData() {
        val toolbar = findViewById<Toolbar>(R.id.last_update_time)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)

        val date = SharedPrefRepository.loadString("lastUpdate", sharedPreferences)
        toolbar.title = if (date.isNullOrEmpty()) "нет данных" else getFormattedDate(date)

        recyclerView.adapter = CurrencyAdapter(SharedPrefRepository.loadCurrency("currencies", sharedPreferences))
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.post(runnable)
    }
}