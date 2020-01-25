package com.silver.weather.OpenWeatherMap

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import com.silver.weather.Interfaces.HttpResponse
import com.silver.weather.Interfaces.weatherByNameInterface
import com.silver.weather.Util.Network
import java.net.URLEncoder

class openWeatherMap(var activity: AppCompatActivity) {
    private val URL_BASE = "http://api.openweathermap.org/"
    private val VERSION = "2.5/"
    private val API_ID = "&appid=a6fb62a4df6500bb3078d7e190bd637e"
    fun getWeatherByName(name: String, unit: String, weatherByNameInterface: weatherByNameInterface) {
        val network = Network(activity)
        val name = URLEncoder.encode(name, "UTF-8")
        val section = "data/"
        val method = "weather?q=$name"
        var url = "$URL_BASE$section$VERSION$method$API_ID$unit"
        network.httpRequest(activity.applicationContext, url, object : HttpResponse {
            override fun httpResponseSuccess(response: String) {
                var gson = Gson()
                var objectResonse = gson.fromJson(response, openWeatherMapAPIName::class.java)
                if (!objectResonse.name.isNullOrEmpty()) {
                    val nameCity = objectResonse.name!!
                    val urlImage = makeIconURL(objectResonse.weather?.get(0)?.icon!!)
                    val status = objectResonse.weather?.get(0)?.main!!
                    val description = objectResonse.weather?.get(0)?.description!!
                    val temperature = objectResonse.main?.temp!!
                    val tempMin = objectResonse.main?.temp_min!!
                    val tempMax = objectResonse.main?.temp_max!!
                    weatherByNameInterface.getWeatherByName(nameCity, urlImage, status, description, temperature, tempMin, tempMax)
                } else {
                    Toast.makeText(activity.applicationContext, "Could not get Weather data", Toast.LENGTH_SHORT).show()
                    activity.finish()
                }
            }
        })
    }


    private fun makeIconURL(icon: String): String {
        val secion = "img/w/"
        val url = "$URL_BASE$secion$icon.png"
        return url
    }
}