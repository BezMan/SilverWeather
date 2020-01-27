package com.silver.weather;

import com.silver.weather.interfaces.IDataSource;
import com.silver.weather.model.WeatherMapApi;
import com.silver.weather.presenter.MainListViewModel;

public class DInjector {

    private static MainListViewModel mainListViewModel;
    private static IDataSource dataSource;


    public static MainListViewModel getViewModel() {
        if (mainListViewModel == null) {
            mainListViewModel = new MainListViewModel(getRepository());
        }
        return mainListViewModel;
    }


    public static IDataSource getRepository() {
        if (dataSource == null) {
            dataSource = new WeatherMapApi();
        }
        return dataSource;
    }


}
