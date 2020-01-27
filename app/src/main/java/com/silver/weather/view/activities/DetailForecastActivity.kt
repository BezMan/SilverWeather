package com.silver.weather.view.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.silver.weather.R
import com.silver.weather.interfaces.IGetForecast
import com.silver.weather.model.Forecast
import com.silver.weather.model.WeatherMapApi
import com.silver.weather.view.adapters.ForecastAdapter
import kotlinx.android.synthetic.main.activity_forecast_detail.*


class DetailForecastActivity : AppCompatActivity() {

    private lateinit var nameCity: String
    private lateinit var weatherUnit: String

    private val weatherMapApi = WeatherMapApi()

    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var listForecasts: ArrayList<Forecast>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_detail)

        listForecasts = ArrayList()
        initToolbar()
        initRecycler()
        getExtras()
        fetchCityForecastData()
    }


    private fun initRecycler() {
        val linearLayoutManager = LinearLayoutManager(this)
        detailRecyclerViewForecast?.layoutManager = linearLayoutManager
        detailRecyclerViewForecast?.setHasFixedSize(true)
        refreshAdapter()
    }

    private fun refreshAdapter() {
        forecastAdapter = ForecastAdapter(this, listForecasts)
        detailRecyclerViewForecast?.adapter = forecastAdapter
        forecastAdapter.notifyDataSetChanged()
    }


    private fun getExtras() {
        nameCity = intent.getStringExtra("CITY")
        weatherUnit = intent.getStringExtra("UNIT")
        detailCityTitle.text = nameCity
    }


    private fun initToolbar() {
        actionBarWeather?.setTitle(R.string.app_name)
        setSupportActionBar(actionBarWeather)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun fetchCityForecastData() {
        weatherMapApi.getForecastByCity(nameCity, weatherUnit, networkCallback())
    }


    private fun networkCallback(): IGetForecast {
        return object : IGetForecast {
            override fun getForecastCallback(cityForecastList: ArrayList<Forecast>) {

                runOnUiThread {
                    listForecasts = ArrayList(cityForecastList)
                    refreshAdapter()
                }
            }
        }
    }



}