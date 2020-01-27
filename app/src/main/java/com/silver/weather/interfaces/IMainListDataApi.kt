package com.silver.weather.interfaces

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.silver.weather.model.CityObj

interface IMainListDataApi {
    fun getWeatherByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<CityObj>>
    fun getListData(): LiveData<ArrayList<CityObj>>

}