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
import com.silver.weather.Interfaces.ClickListener
import com.silver.weather.R
import com.silver.weather.model.City
import com.silver.weather.view.activities.WeatherActivity
import com.silver.weather.view.adapters.CityListAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    companion object {
        private var listCities: ArrayList<City>? = null
        private var recyclerViewCities: RecyclerView? = null
        private var citiesListAdapter: CityListAdapter? = null

        private var layoutManager: RecyclerView.LayoutManager? = null
        private var fragmentLayout: View? = null

        fun receiveData(query: String, submit: Boolean) {
            if (submit) {
                goWeatherResult(query, fragmentLayout?.context!!)
            } else {
                listCities = citiesListAdapter?.filter(query)
            }
        }

        fun goWeatherResult(cityName: String, context: Context) {
            val intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra("CITY", cityName)
            context.startActivity(intent)
        }
    }

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLayout = inflater.inflate(R.layout.fragment_list, container, false)
        fetchCitiesList()
        configureRecyclerView()

        return fragmentLayout
    }

    private fun configureRecyclerView() {
        recyclerViewCities = fragmentLayout?.rootView?.recyclerViewCities
        recyclerViewCities?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(fragmentLayout?.context)
        recyclerViewCities?.layoutManager = layoutManager

        citiesListAdapter = CityListAdapter(fragmentLayout?.context!!, listCities!!, object : ClickListener {
            override fun onClick(view: View, index: Int) {
                val index = listCities?.get(index)
                val cityName = index?.nameCity!!
                goWeatherResult(cityName, fragmentLayout!!.context)
            }
        })
        recyclerViewCities?.adapter = citiesListAdapter
    }

    private fun fetchCitiesList() {
        listCities = ArrayList()
        val cities = resources.getStringArray(R.array.cityStrArray)
        for (cityName in cities) {
            listCities?.add(City(cityName, "sub"))
        }
    }


}
