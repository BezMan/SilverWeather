package com.silver.weather.view.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.silver.weather.R
import com.silver.weather.interfaces.IGetWeather
import com.silver.weather.model.CityObj
import com.silver.weather.model.WeatherMapApi
import kotlinx.android.synthetic.main.activity_weather.*


class DetailForecastActivity : AppCompatActivity() {
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
        val openWeatherMap = WeatherMapApi(this)
        openWeatherMap.getWeatherByName(nameCity, unit, networkCallback(unit))
    }

    private fun networkCallback(unit: String): IGetWeather {
        return object : IGetWeather {
            override fun getWeatherByName(cityObj: CityObj) {
                this@DetailForecastActivity.runOnUiThread {

                    val m = if (unit.isNotEmpty()) "°C" else "°F"

                    tvCity?.text = nameCity
                    tvTempMax?.text = "Temp max: ${cityObj.tempMax}$m"
                    tvTempMin?.text = "Temp min: ${cityObj.tempMin}$m"
                    tvDescription?.text = "Description: ${cityObj.description}"

                    Glide.with(this@DetailForecastActivity).load(cityObj.urlImage).into(iconImg)
                }
            }
        }
    }

}