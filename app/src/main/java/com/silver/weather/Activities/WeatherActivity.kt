package com.silver.weather.Activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.silver.weather.Interfaces.weatherByNameInterface
import com.silver.weather.OpenWeatherMap.openWeatherMap
import com.silver.weather.R


class WeatherActivity : AppCompatActivity() {
    var nameCity: String? = null
    var lat: String? = null
    var lon: String? = null
    var unit: String? = null

    var toolbar: Toolbar? = null
    var ivStatus: ImageView? = null
    var tvCity: TextView? = null
    var tvTemperature: TextView? = null
    var tvStatus: TextView? = null
    var tvDescription: TextView? = null
    var tvTempMin: TextView? = null
    var tvTempMax: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        initViews()
        initToolbar()
        getExtras()

        unit = "&units=metric"
        getWeatherData(nameCity!!, unit!!)

    }

    private fun getExtras() {
        nameCity = intent.getStringExtra("CITY")
        lon = intent.getStringExtra("LON")
        lat = intent.getStringExtra("LAT")
    }

    private fun initViews() {
        ivStatus = findViewById(R.id.ivStatus)
        tvCity = findViewById(R.id.tvCity)
        tvStatus = findViewById(R.id.tvStatus)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvDescription = findViewById(R.id.tvDescription)
        tvTempMax = findViewById(R.id.tvTempMax)
        tvTempMin = findViewById(R.id.tvTempMin)
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.actionBarWeather)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getWeatherData(nameCity: String, unit: String) {
        val openWeatherMap = openWeatherMap(this)
        openWeatherMap.getWeatherByName(nameCity, unit, object : weatherByNameInterface {
            override fun getWeatherByName(nameCity: String, urlImage: String, status: String, description: String, temperature: String, tempMin: String, tempMax: String) {
                this@WeatherActivity.runOnUiThread {
                    tvDescription?.text = description
                    tvCity?.text = nameCity
                    val m = if (!unit.isNullOrEmpty()) "°C" else "°F"

                    tvTemperature?.text = "Temperature: $temperature$m"
                    tvTempMax?.text = "Temp max: $tempMax$m"
                    tvTempMin?.text = "Temp min: $tempMin$m"

                    tvStatus?.text = status
                    tvDescription?.text = "Description: $description"
                    Log.d("IMAGE", urlImage)
                    Glide.with(this@WeatherActivity).load(urlImage).into(ivStatus)
                }
            }
        })
    }

}