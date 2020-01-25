package com.silver.weather.view.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.silver.weather.Interfaces.weatherByNameInterface
import com.silver.weather.R
import com.silver.weather.model.WeatherMapRequests
import kotlinx.android.synthetic.main.activity_weather.*


class WeatherActivity : AppCompatActivity() {
    private lateinit var nameCity: String
    private lateinit var unit: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        initToolbar()
        getExtras()

        unit = "&units=metric"
        getWeatherData(nameCity, unit)

    }

    private fun getExtras() {
        nameCity = intent.getStringExtra("CITY")
    }


    private fun initToolbar() {
        actionBarWeather?.setTitle(R.string.app_name)
        setSupportActionBar(actionBarWeather)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getWeatherData(nameCity: String, unit: String) {
        val openWeatherMap = WeatherMapRequests(this)
        openWeatherMap.getWeatherByName(nameCity, unit, object : weatherByNameInterface {
            override fun getWeatherByName(nameCity: String, urlImage: String, status: String, description: String, temperature: String, tempMin: String, tempMax: String) {
                this@WeatherActivity.runOnUiThread {
                    tvDescription?.text = description
                    tvCity?.text = nameCity
                    val m = if (unit.isNotEmpty()) "°C" else "°F"

                    tvTemperature?.text = "Temperature: $temperature$m"
                    tvTempMax?.text = "Temp max: $tempMax$m"
                    tvTempMin?.text = "Temp min: $tempMin$m"

                    tvStatus?.text = status
                    tvDescription?.text = "Description: $description"
                    Log.d("IMAGE", urlImage)
                    Glide.with(this@WeatherActivity).load(urlImage).into(iconImg)
                }
            }
        })
    }

}