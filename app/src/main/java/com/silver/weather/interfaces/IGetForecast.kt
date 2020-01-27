package com.silver.weather.interfaces

import com.silver.weather.model.Forecast


interface IGetForecast {
    fun getForecastCallback(cityForecastList: List<Forecast>)
}