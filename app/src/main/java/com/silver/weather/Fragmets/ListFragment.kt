package com.silver.weather.Fragmets


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.silver.weather.Activities.WeatherActivity
import com.silver.weather.Adapters.City
import com.silver.weather.Adapters.CityAdapter
import com.silver.weather.Interfaces.ClickListener
import com.silver.weather.R
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {
    var listCities: ArrayList<City>? = null

    private var recyclerViewCities: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    companion object {
        var fragmentLayout: View? = null
        var citiesAdapter: CityAdapter? = null
        fun receiveData(query: String, submit: Boolean) {
            if (submit) {
                goWeatherResult(query, fragmentLayout?.context!!)
            } else {
                citiesAdapter?.filter(query)
            }

        }

        fun goWeatherResult(cityName: String, context: Context) {
            val intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra("CITY", cityName)
            context.startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLayout = inflater.inflate(R.layout.fragment_list, container, false)
        loadData()
        configureRecyclerView()

        return fragmentLayout
    }

    private fun configureRecyclerView() {
        recyclerViewCities = fragmentLayout?.rootView?.recyclerViewCities
        recyclerViewCities?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(fragmentLayout?.context)
        recyclerViewCities?.layoutManager = layoutManager
        citiesAdapter = CityAdapter(fragmentLayout?.context!!, listCities!!, object : ClickListener {
            override fun onClick(view: View, index: Int) {
                goWeatherResult(listCities?.get(index)?.nameCity!!, fragmentLayout!!.context)
            }
        })
        recyclerViewCities?.adapter = citiesAdapter
    }

    private fun loadData() {
        listCities = ArrayList()
        val cities = resources.getStringArray(R.array.cityStrArray)
        for (cityName in cities) {
            listCities?.add(City(cityName, "sub"))
        }
    }


}
