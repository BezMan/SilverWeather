package com.silver.weather.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.silver.weather.interfaces.IMainListDataApi

class RepoViewModelFactory(private val dataSource: IMainListDataApi) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainListViewModel(dataSource) as T
    }

}