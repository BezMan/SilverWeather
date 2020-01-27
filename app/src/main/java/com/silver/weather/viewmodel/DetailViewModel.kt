package com.silver.weather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.silver.weather.interfaces.IDetailDataApi
import com.silver.weather.model.Forecast

class DetailViewModel(private val mRemoteDataSource: IDetailDataApi) : ViewModel() {

    fun getForecastByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<Forecast>> {
        return mRemoteDataSource.getForecastByCity(cityName, unit)
    }

    fun getForecastData(): LiveData<ArrayList<Forecast>> {
        return mRemoteDataSource.getForecastData()
    }


}