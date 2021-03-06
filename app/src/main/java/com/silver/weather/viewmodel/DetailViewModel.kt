package com.silver.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silver.weather.interfaces.IDetailDataApi
import com.silver.weather.model.Forecast

class DetailViewModel(private val mRemoteDataSource: IDetailDataApi) : ViewModel() {

    fun getForecastByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<Forecast>> {
        return mRemoteDataSource.getForecastByCity(cityName, unit)
    }

    val observedDetailForecastList = mRemoteDataSource.getForecastData()


}