package com.silver.weather.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.gson.Gson
import com.silver.weather.interfaces.IMainListDataApi
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class WeatherNetwork : IMainListDataApi {

    private val URL_BASE = "http://api.openweathermap.org/"
    private val VERSION = "data/2.5/"
    private val API_ID = "&appid=a6fb62a4df6500bb3078d7e190bd637e"

    var cityList = ArrayList<CityObj>()
    var liveCityList = MutableLiveData<ArrayList<CityObj>>()


    override fun getWeatherByCity(cityName: String, unit: String?): MutableLiveData<ArrayList<CityObj>> {
        val method = "weather?q=$cityName"
        val url = "$URL_BASE$VERSION$method$API_ID$unit"

        cityList.clear()
        liveCityList.value = cityList
        weatherHttpRequest(url, weatherDataCallback())

        return liveCityList
    }


    private fun weatherDataCallback(): HttpResponse {
        return object : HttpResponse {
            override fun httpResponseSuccess(response: String) {
                val cityData = Gson().fromJson(response, CityWeather::class.java)
                if (cityData.name.isNotEmpty()) {
                    val cityObj = CityObj(cityData.name
                            , makeIconURL(cityData.weather[0].icon)
                            , cityData.weather[0].description
                            , cityData.main.temp_min.toString()
                            , cityData.main.temp_max.toString())


                    cityList.add(cityObj)
                    liveCityList.postValue(cityList)

                }
            }
        }
    }

    override fun getListData(): LiveData<ArrayList<CityObj>> {
        return liveCityList
    }

    fun getForecastByCity(cityName: String, unit: String?, weather: IGetForecast) {
        val method = "forecast?q=$cityName"
        val url = "$URL_BASE$VERSION$method$API_ID$unit"

        forecastHttpRequest(url, forecastDataCallback(weather))
    }

    private fun forecastDataCallback(weather: IGetForecast): HttpResponse {
        return object : HttpResponse {
            override fun httpResponseSuccess(response: String) {
                val cityData = Gson().fromJson(response, CityForecast::class.java)

                val forecastList = ArrayList(cityData.list)
                weather.getForecastCallback(forecastList)
            }
        }
    }


    private fun makeIconURL(icon: String): String {
        return "${URL_BASE}img/w/$icon.png"
    }


    private fun weatherHttpRequest(url: String, httpResponse: HttpResponse) {
        Log.d("URL_REQUEST", url)
            val client = OkHttpClient()
            val request = okhttp3.Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : okhttp3.Callback {

                override fun onResponse(call: Call?, response: Response?) {
                    Log.d("HTTP_REQUEST", response?.body().toString())
                    httpResponse.httpResponseSuccess(response?.body()?.string()!!)
                }

                override fun onFailure(call: Call?, e: IOException?) {
                }
            })
        }


    private fun forecastHttpRequest(url: String, httpResponse: HttpResponse) {
        Log.d("URL_REQUEST", url)
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("HTTP_REQUEST", response?.body().toString())
                httpResponse.httpResponseSuccess(response?.body()?.string()!!)
            }

            override fun onFailure(call: Call?, e: IOException?) {
            }
        })
    }


    interface HttpResponse {
        fun httpResponseSuccess(response: String)
    }

    interface IGetForecast {
        fun getForecastCallback(cityForecastList: ArrayList<Forecast>)
    }

}