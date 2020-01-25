package com.silver.weather.interfaces

import com.silver.weather.model.CityObj


interface IGetWeather {
    fun getWeatherByName(cityObj: CityObj)
}