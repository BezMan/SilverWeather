package com.silver.weather.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.silver.weather.R
import com.silver.weather.cache.SharedPrefs
import com.silver.weather.cache.SharedPrefs.CELSIUS
import com.silver.weather.cache.SharedPrefs.FAHRENHEIT
import com.silver.weather.interfaces.IClickListener
import com.silver.weather.interfaces.IGetWeather
import com.silver.weather.model.CityObj
import com.silver.weather.model.WeatherMapApi
import com.silver.weather.view.adapters.CityListAdapter
import kotlinx.android.synthetic.main.activity_main_list.*

class MainListActivity : AppCompatActivity() {

    private lateinit var listCities: ArrayList<String>
    private lateinit var listCityObjects: ArrayList<CityObj>
    private var recyclerViewCities: RecyclerView? = null
    private lateinit var citiesListAdapter: CityListAdapter

    private var layoutManager: RecyclerView.LayoutManager? = null
    private val weatherMapApi = WeatherMapApi()
    private lateinit var searchView: SearchView

    private var savedWeatherUnit: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)

        listCityObjects = ArrayList()

        fetchCitiesList()
        configureRecyclerView()

        savedWeatherUnit = SharedPrefs.loadStringData(SharedPrefs.UNIT_KEY)

        fetchAllCitiesData(savedWeatherUnit)
    }

    private fun fetchAllCitiesData(unit: String?) {
        listCityObjects.clear()
        citiesListAdapter = CityListAdapter(this@MainListActivity, listCityObjects, clickListenerCallback())
        recyclerViewCities?.adapter = citiesListAdapter

        for (cityName in listCities) {
            weatherMapApi.getWeatherByName(cityName, unit, networkCallback(unit))
        }
    }

    fun goDetailCityClicked(cityName: String, context: Context) {
        val intent = Intent(context, DetailForecastActivity::class.java)
        intent.putExtra("CITY", cityName)
        context.startActivity(intent)
    }


    private fun configureRecyclerView() {
        recyclerViewCities = findViewById(R.id.recyclerViewCities)
        layoutManager = LinearLayoutManager(this)
        recyclerViewCities?.layoutManager = layoutManager
        recyclerViewCities?.setHasFixedSize(true)
    }

    private fun clickListenerCallback(): IClickListener {
        return object : IClickListener {
            override fun onClick(view: View, index: Int) {
                val cityName = listCities[index]
                goDetailCityClicked(cityName, this@MainListActivity)
            }
        }
    }


    private fun fetchCitiesList() {
        listCities = ArrayList()
        val cities = resources.getStringArray(R.array.cityStrArray)
        for (cityName in cities) {
            listCities.add(cityName)
        }
    }

    private fun networkCallback(unit: String?): IGetWeather {
        return object : IGetWeather {
            override fun getWeatherCallback(cityObj: CityObj) {
                listCityObjects.add(cityObj)
                this@MainListActivity.runOnUiThread {
                    citiesListAdapter.notifyDataSetChanged()
                }
            }
        }
    }


    fun searchFilter(text: String) {
        citiesListAdapter.filter(text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_choose, menu)

        val itemSearch = menu?.findItem(R.id.icSearch)
        initSearchView(itemSearch)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initSearchView(itemSearch: MenuItem?) {
        searchView = itemSearch?.actionView as SearchView
        searchView.queryHint = "Write city..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //when the user presses enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                //                searchSubmit(query!!)
                return true
            }

            //when the text changes
            override fun onQueryTextChange(newText: String?): Boolean {
                searchFilter(newText!!)
                return true
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.icMap -> {
                savedWeatherUnit = if (savedWeatherUnit == CELSIUS) FAHRENHEIT else CELSIUS
                SharedPrefs.saveStringData(SharedPrefs.UNIT_KEY, savedWeatherUnit)
                recreate()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
