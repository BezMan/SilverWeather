package com.silver.weather.interfaces

import com.silver.weather.model.CityObj


interface IGetWeather {
    fun getWeatherCallback(cityObj: CityObj)
}