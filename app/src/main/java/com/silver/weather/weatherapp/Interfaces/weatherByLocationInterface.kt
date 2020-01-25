package com.silver.weather.Interfaces

import com.silver.weather.OpenWeatherMap.openWeatherMapAPILocation


interface weatherByLocationInterface {
    fun getWeatherByLocation(result: openWeatherMapAPILocation)
}