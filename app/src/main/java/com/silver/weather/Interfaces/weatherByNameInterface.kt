package com.silver.weather.Interfaces


interface weatherByNameInterface {
    fun getWeatherByName(nameCity: String, urlImage: String, status: String, description: String, temperature: String, tempMin: String, tempMax: String)
}