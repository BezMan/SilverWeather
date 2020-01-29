package com.silver.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.silver.weather.interfaces.IDetailDataApi

class DetailViewModelFactory(private val dataSource: IDetailDataApi) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(dataSource) as T
    }

}