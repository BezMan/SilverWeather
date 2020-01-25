package com.silver.weather.interfaces


interface IGetWeather {
    fun getWeatherByName(nameCity: String, urlImage: String, status: String, description: String, temperature: String, tempMin: String, tempMax: String)
}