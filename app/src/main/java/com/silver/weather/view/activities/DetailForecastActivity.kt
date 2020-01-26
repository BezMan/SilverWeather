package com.silver.weather.view.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.silver.weather.R
import kotlinx.android.synthetic.main.activity_forecast_detail.*


class DetailForecastActivity : AppCompatActivity() {

    private lateinit var nameCity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_detail)
        initToolbar()
        getExtras()
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


}