package com.silver.weather.model

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.silver.weather.interfaces.IGetWeather
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class WeatherMapApi(var activity: AppCompatActivity) {

    private val URL_BASE = "http://api.openweathermap.org/"
    private val VERSION = "data/2.5/"
    private val API_ID = "&appid=a6fb62a4df6500bb3078d7e190bd637e"

    fun getWeatherByName(cityName: String, unit: String, IGetWeather: IGetWeather) {
        val method = "weather?q=$cityName"
        val url = "$URL_BASE$VERSION$method$API_ID$unit"

        httpRequest(url, responseCallback(IGetWeather))
    }

    private fun responseCallback(IGetWeather: IGetWeather): HttpResponse {
        return object : HttpResponse {
            override fun httpResponseSuccess(response: String) {
                val gson = Gson()
                val objectResonse = gson.fromJson(response, CityWeather::class.java)
                if (!objectResonse.name.isNullOrEmpty()) {
                    val nameCity = objectResonse.name
                    val urlImage = makeIconURL(objectResonse.weather.get(0).icon)
                    val status = objectResonse.weather.get(0).main
                    val description = objectResonse.weather.get(0).description
                    val temperature = objectResonse.main.temp
                    val tempMin = objectResonse.main.temp_min
                    val tempMax = objectResonse.main.temp_max
                    IGetWeather.getWeatherByName(nameCity, urlImage, status, description, temperature.toString(), tempMin.toString(), tempMax.toString())
                } else {
                    Toast.makeText(activity.applicationContext, "Could not get Weather data", Toast.LENGTH_SHORT).show()
                    activity.finish()
                }
            }
        }
    }


    private fun makeIconURL(icon: String): String {
        return "${URL_BASE}img/w/$icon.png"
    }


    private fun verifyAvailableNetwork(): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    private fun httpRequest(url: String, httpResponse: HttpResponse) {
        Log.d("URL_REQUEST", url)
        if (verifyAvailableNetwork()) {
            val client = OkHttpClient()
            val request = okhttp3.Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : okhttp3.Callback {

                override fun onResponse(call: Call?, response: Response?) {
                    Log.d("HTTP_REQUEST", response?.body().toString())
                    httpResponse.httpResponseSuccess(response?.body()?.string()!!)
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    Toast.makeText(activity.applicationContext, "Error in HTTP request", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(activity.applicationContext, "No network available", Toast.LENGTH_SHORT).show()
        }
    }


    interface HttpResponse {
        fun httpResponseSuccess(response: String)
    }

}