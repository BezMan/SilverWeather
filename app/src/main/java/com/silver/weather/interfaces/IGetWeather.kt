package com.silver.weather.interfaces


interface IGetWeather {
    fun getWeatherByName(nameCity: String, urlImage: String, description: String, tempMin: String, tempMax: String)
}