package com.silver.weather.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.silver.weather.interfaces.IDataSource
import com.silver.weather.model.CityObj

class MainListViewModel(private val mRemoteDataSource: IDataSource) : ViewModel() {

    fun getWeatherByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<CityObj>> {
        return mRemoteDataSource.getWeatherByCity(cityName, unit)
    }

    fun getListData(): LiveData<ArrayList<CityObj>> {
        return mRemoteDataSource.getListData()
    }


}