package com.silver.weather;

import com.silver.weather.interfaces.IDetailDataApi;
import com.silver.weather.interfaces.IMainListDataApi;
import com.silver.weather.model.WeatherNetwork;
import com.silver.weather.viewmodel.DetailViewModel;
import com.silver.weather.viewmodel.MainListViewModel;

public class DInjector {

    private static MainListViewModel mainListViewModel;
    private static DetailViewModel detailViewModel;
    private static IMainListDataApi mainListDataSource;
    private static IDetailDataApi DetailDataSource;


    public static MainListViewModel getViewModel() {
        if (mainListViewModel == null) {
            mainListViewModel = new MainListViewModel(getMainRepository());
        }
        return mainListViewModel;
    }

    public static DetailViewModel getDetailViewModel() {
        if (detailViewModel == null) {
            detailViewModel = new DetailViewModel(getDetailRepository());
        }
        return detailViewModel;
    }


    public static IMainListDataApi getMainRepository() {
        if (mainListDataSource == null) {
            mainListDataSource = new WeatherNetwork();
        }
        return mainListDataSource;
    }

    public static IDetailDataApi getDetailRepository() {
        if (DetailDataSource == null) {
            DetailDataSource = new WeatherNetwork();
        }
        return DetailDataSource;
    }


}
