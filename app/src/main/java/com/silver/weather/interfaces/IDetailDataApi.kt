package com.silver.weather.interfaces

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.silver.weather.model.Forecast

interface IDetailDataApi {
    fun getForecastByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<Forecast>>
    fun getListData(): LiveData<ArrayList<Forecast>>

}