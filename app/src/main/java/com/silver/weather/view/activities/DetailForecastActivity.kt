package com.silver.weather.view.activities


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.silver.weather.DInjector
import com.silver.weather.R
import com.silver.weather.model.Forecast
import com.silver.weather.view.adapters.ForecastAdapter
import com.silver.weather.viewmodel.DetailViewModel
import com.silver.weather.viewmodel.DetailViewModelFactory
import kotlinx.android.synthetic.main.activity_forecast_detail.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.ExperimentalTime


@ExperimentalTime
class DetailForecastActivity : AppCompatActivity() {

    private var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private var customDateFormat: SimpleDateFormat = SimpleDateFormat("EEE, MMM d", Locale.US)

    private lateinit var nameCity: String
    private lateinit var weatherUnit: String

    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var listForecasts: ArrayList<Forecast>

    private lateinit var mViewModel: DetailViewModel

    private val dataObserver: Observer<ArrayList<Forecast>> = Observer { list: ArrayList<Forecast>? -> dataCallback(list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_detail)

        listForecasts = ArrayList()
        initToolbar()
        initRecycler()
        getExtras()

        initViewModel()
        setObservers()

        fetchCityForecastData()
    }


    private fun initViewModel() {
        mViewModel = DInjector.getDetailViewModel()
        mViewModel = ViewModelProvider(this, DetailViewModelFactory(DInjector.getDetailRepository())).get(DetailViewModel::class.java)
    }

    private fun setObservers() {
        mViewModel.getForecastData().observe(this, dataObserver)
    }

    private fun dataCallback(cityForecastList: ArrayList<Forecast>?) {

        val filteredList = cityForecastList?.filter {
            hoursFilter(it)
        }?.map {
            customDate(it)
        }

        listForecasts = ArrayList(filteredList!!)
        refreshAdapter()
    }


    private fun initRecycler() {
        val linearLayoutManager = LinearLayoutManager(this)
        detailRecyclerViewForecast?.layoutManager = linearLayoutManager
        detailRecyclerViewForecast?.setHasFixedSize(true)
        refreshAdapter()
    }

    private fun refreshAdapter() {
        forecastAdapter = ForecastAdapter(this, listForecasts)
        detailRecyclerViewForecast?.adapter = forecastAdapter
        forecastAdapter.notifyDataSetChanged()
    }


    private fun getExtras() {
        nameCity = intent.getStringExtra("CITY")
        weatherUnit = intent.getStringExtra("UNIT")
        detailCityTitle.text = nameCity
    }


    private fun initToolbar() {
        actionBarWeather?.setTitle(R.string.app_name)
        setSupportActionBar(actionBarWeather)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun fetchCityForecastData() {
        mViewModel.getForecastByCity(nameCity, weatherUnit)
    }


    private fun customDate(it: Forecast): Forecast {
        val date = dateFormat.parse(it.dt_txt) //create a date object out of data
        it.custom_dt_txt = customDateFormat.format(date)
        return it
    }


    private fun hoursFilter(forecast: Forecast): Boolean {
        val cal = Calendar.getInstance()
        cal.time = dateFormat.parse(forecast.dt_txt)
        val hours = cal[Calendar.HOUR_OF_DAY]
        return (hours in 21..23)
    }


}