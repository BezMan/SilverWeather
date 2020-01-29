package com.silver.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silver.weather.interfaces.IMainListDataApi
import com.silver.weather.model.CityObj

class MainListViewModel(private val mRemoteDataSource: IMainListDataApi) : ViewModel() {

    fun getWeatherByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<CityObj>> {
        return mRemoteDataSource.getWeatherByCity(cityName, unit)
    }

    val observedCityList = mRemoteDataSource.getCityList()


}