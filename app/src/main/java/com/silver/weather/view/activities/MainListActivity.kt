package com.silver.weather.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.silver.weather.R
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
    private val openWeatherMap = WeatherMapApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)

        fetchCitiesList()
        configureRecyclerView()

        fetchCityWeatherData()

    }

    private fun fetchCityWeatherData() {
        val unit = "&units=metric"
        for (cityName in listCities) {
            openWeatherMap.getWeatherByName(cityName, unit, networkCallback(unit))
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

        citiesListAdapter = CityListAdapter(this, listCityObjects, clickListenerCallback())
        recyclerViewCities?.adapter = citiesListAdapter
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
        listCityObjects = ArrayList()
        val cities = resources.getStringArray(R.array.cityStrArray)
        for (cityName in cities) {
            listCities.add(cityName)
        }
    }

    private fun networkCallback(unit: String): IGetWeather {
        return object : IGetWeather {
            override fun getWeatherByName(cityObj: CityObj) {
                listCityObjects.add(cityObj)
                this@MainListActivity.runOnUiThread {
                    citiesListAdapter.notifyDataSetChanged()
                }
            }
        }
    }

//    fun searchSubmit(text: String) {
//        goWeatherResult(text, this)
//    }

    fun searchFilter(text: String) {
        listCityObjects = citiesListAdapter.filter(text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_choose, menu)

        val itemSearch = menu?.findItem(R.id.icSearch)
        val viewSearch = itemSearch?.actionView as android.support.v7.widget.SearchView
        viewSearch.queryHint = "Write city..."

        viewSearch.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {

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
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
//            R.id.icMap -> {
//                val intent = Intent(this, MapsActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//            R.id.icLocation -> {
//                if (!lat.isNullOrEmpty() && !lng.isNullOrEmpty()) {
//                    val intent = Intent(applicationContext, WeatherActivity::class.java)
//                    intent.putExtra("LAT", lat)
//                    intent.putExtra("LON", lng)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(applicationContext, "Without location", Toast.LENGTH_SHORT).show()
//                }
//
//                return true
//            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
