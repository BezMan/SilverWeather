package com.silver.weather.presenter

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.silver.weather.interfaces.IDataSource

class RepoViewModelFactory(private val dataSource: IDataSource) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainListViewModel(dataSource) as T
    }

}