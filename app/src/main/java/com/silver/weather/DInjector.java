package com.silver.weather;

import com.silver.weather.interfaces.IMainListDataApi;
import com.silver.weather.model.WeatherNetwork;
import com.silver.weather.viewmodel.MainListViewModel;

public class DInjector {

    private static MainListViewModel mainListViewModel;
    private static IMainListDataApi dataSource;


    public static MainListViewModel getViewModel() {
        if (mainListViewModel == null) {
            mainListViewModel = new MainListViewModel(getRepository());
        }
        return mainListViewModel;
    }


    public static IMainListDataApi getRepository() {
        if (dataSource == null) {
            dataSource = new WeatherNetwork();
        }
        return dataSource;
    }


}
