package com.silver.weather.view.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.silver.weather.R
import com.silver.weather.interfaces.IClickListener
import com.silver.weather.interfaces.IGetWeather
import com.silver.weather.model.CityObj
import com.silver.weather.model.WeatherMapApi
import com.silver.weather.view.activities.DetailForecastActivity
import com.silver.weather.view.adapters.CityListAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    companion object {
        private lateinit var listCities: ArrayList<String>
        private lateinit var listCityObjects: ArrayList<CityObj>
        private var recyclerViewCities: RecyclerView? = null
        private lateinit var citiesListAdapter: CityListAdapter

        private var layoutManager: RecyclerView.LayoutManager? = null
        private var fragmentLayout: View? = null
        val openWeatherMap = WeatherMapApi()

        fun receiveData(query: String, submit: Boolean) {
            if (submit) {
                goWeatherResult(query, fragmentLayout?.context!!)
            } else {
                listCityObjects = citiesListAdapter.filter(query)
            }
        }

        fun goWeatherResult(cityName: String, context: Context) {
            val intent = Intent(context, DetailForecastActivity::class.java)
            intent.putExtra("CITY", cityName)
            context.startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLayout = inflater.inflate(R.layout.fragment_list, container, false)
        return fragmentLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchCitiesList()
        configureRecyclerView()

        val unit = "&units=metric"
        for (cityName in listCities) {
            getWeatherData(cityName, unit)
        }
    }


    private fun getWeatherData(nameCity: String, unit: String) {
        openWeatherMap.getWeatherByName(nameCity, unit, networkCallback(unit))
    }

    private fun networkCallback(unit: String): IGetWeather {
        return object : IGetWeather {
            override fun getWeatherByName(cityObj: CityObj) {
                listCityObjects.add(cityObj)
                activity?.runOnUiThread {
                    citiesListAdapter.notifyDataSetChanged()
                }
            }
        }
    }
    private fun configureRecyclerView() {
        recyclerViewCities = fragmentLayout?.rootView?.recyclerViewCities
        recyclerViewCities?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(fragmentLayout?.context)
        recyclerViewCities?.layoutManager = layoutManager

        citiesListAdapter = CityListAdapter(fragmentLayout?.context!!, listCityObjects, clickListenerCallback())
        recyclerViewCities?.adapter = citiesListAdapter
    }

    private fun clickListenerCallback(): IClickListener {
        return object : IClickListener {
            override fun onClick(view: View, index: Int) {
                val cityName = listCities[index]
                goWeatherResult(cityName, fragmentLayout!!.context)
            }
        }
    }

    private fun fetchCitiesList() {
        listCities = ArrayList()
        listCityObjects = ArrayList()
        val cities = resources.getStringArray(R.array.cityStrArray)
        for (cityName in cities) {
            listCities.add(cityName)
        }
    }


}
