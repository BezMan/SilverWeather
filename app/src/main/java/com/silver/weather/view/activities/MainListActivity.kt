package com.silver.weather.view.activities

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
import com.silver.weather.interfaces.IGetWeather
import com.silver.weather.model.CityObj
import com.silver.weather.model.WeatherMapApi
import com.silver.weather.view.adapters.CityListAdapter
import kotlinx.android.synthetic.main.activity_main_list.*

class MainListActivity : AppCompatActivity(), CityListAdapter.ItemClickListener {

    private lateinit var listCities: ArrayList<String>
    private lateinit var listCityObjects: ArrayList<CityObj>
    private lateinit var citiesListAdapter: CityListAdapter

    private var layoutManager: RecyclerView.LayoutManager? = null
    private val weatherMapApi = WeatherMapApi()
    private lateinit var searchView: SearchView

    private var storedWeatherUnit: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)

        listCityObjects = ArrayList()
        storedWeatherUnit = SharedPrefs.loadStringData(SharedPrefs.UNIT_KEY)

        fetchCitiesList()
        configureRecyclerView()
        fetchAllCitiesData(storedWeatherUnit)
    }

    private fun fetchAllCitiesData(unit: String?) {
        listCityObjects.clear()
        citiesListAdapter = CityListAdapter(this, listCityObjects)
        citiesListAdapter.setClickListener(this)
        recyclerViewCities?.adapter = citiesListAdapter

        for (cityName in listCities) {
            weatherMapApi.getWeatherByCity(cityName, unit, networkCallback())
        }
    }



    private fun configureRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        recyclerViewCities?.layoutManager = layoutManager
        recyclerViewCities?.setHasFixedSize(true)
    }



    private fun fetchCitiesList() {
        listCities = ArrayList()
        val cities = resources.getStringArray(R.array.cityStrArray)
        for (cityName in cities) {
            listCities.add(cityName)
        }
    }

    private fun networkCallback(): IGetWeather {
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

        val itemWeatherUnit = menu?.findItem(R.id.icUnit)
        if (storedWeatherUnit == FAHRENHEIT) {
            itemWeatherUnit?.title = "°F"
        }

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
            R.id.icUnit -> {
                storedWeatherUnit = if (storedWeatherUnit == CELSIUS) FAHRENHEIT else CELSIUS
                SharedPrefs.saveStringData(SharedPrefs.UNIT_KEY, storedWeatherUnit)
                recreate()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(view: View?, index: Int) {
        val cityName = listCities[index]
        val intent = Intent(this@MainListActivity, DetailForecastActivity::class.java)
        intent.putExtra("CITY", cityName)
        intent.putExtra("UNIT", storedWeatherUnit)
        this@MainListActivity.startActivity(intent)

    }

}
