package com.silver.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.silver.weather.interfaces.IMainListDataApi

class MainListViewModelFactory(private val dataSource: IMainListDataApi) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainListViewModel(dataSource) as T
    }

}