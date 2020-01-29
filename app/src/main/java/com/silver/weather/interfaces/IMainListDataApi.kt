package com.silver.weather.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.silver.weather.model.CityObj

interface IMainListDataApi {
    fun getWeatherByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<CityObj>>
    fun getCityList(): LiveData<ArrayList<CityObj>>

}