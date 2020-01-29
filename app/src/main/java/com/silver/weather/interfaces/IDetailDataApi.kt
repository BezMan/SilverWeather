package com.silver.weather.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.silver.weather.model.Forecast

interface IDetailDataApi {
    fun getForecastByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<Forecast>>
    fun getForecastData(): LiveData<ArrayList<Forecast>>

}