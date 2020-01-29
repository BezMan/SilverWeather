package com.silver.weather.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.silver.weather.DInjector
import com.silver.weather.R
import com.silver.weather.cache.SharedPrefs
import com.silver.weather.cache.SharedPrefs.CELSIUS
import com.silver.weather.cache.SharedPrefs.FAHRENHEIT
import com.silver.weather.model.CityObj
import com.silver.weather.view.adapters.CityListAdapter
import com.silver.weather.viewmodel.MainListViewModel
import com.silver.weather.viewmodel.MainListViewModelFactory
import kotlinx.android.synthetic.main.activity_main_list.*
import kotlin.time.ExperimentalTime

class MainListActivity : AppCompatActivity(), CityListAdapter.ItemClickListener {

    private lateinit var listCities: ArrayList<String>
    private lateinit var listCityObjects: ArrayList<CityObj>
    private lateinit var citiesListAdapter: CityListAdapter

    private lateinit var searchView: SearchView

    private lateinit var storedWeatherUnit: String
    private lateinit var mViewModel: MainListViewModel

    private val dataObserver: Observer<ArrayList<CityObj>> = Observer { list: ArrayList<CityObj>? ->
        dataCallback(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)

        listCityObjects = ArrayList()
        storedWeatherUnit = SharedPrefs.loadStringData(SharedPrefs.UNIT_KEY)

        fetchCitiesResourceList()
        configureRecyclerView()
        initViewModel()
        setObservers()


        fetchAllCitiesData(storedWeatherUnit)
    }


    private fun initViewModel() {
        mViewModel = DInjector.getViewModel()
        mViewModel = ViewModelProvider(this, MainListViewModelFactory(DInjector.getMainRepository())).get(MainListViewModel::class.java)
    }

    private fun setObservers() {
        mViewModel.observedCityList.observe(this, dataObserver)
    }

    private fun dataCallback(data: ArrayList<CityObj>?) {
        listCityObjects.clear()
        listCityObjects = ArrayList(data!!)

        citiesListAdapter = CityListAdapter(this, listCityObjects)
        citiesListAdapter.setClickListener(this)
        recyclerViewCities?.adapter = citiesListAdapter

        citiesListAdapter.notifyDataSetChanged()
    }


    private fun fetchAllCitiesData(unit: String?) {
        for (cityName in listCities) {
            mViewModel.getWeatherByCity(cityName, unit)
        }
    }



    private fun configureRecyclerView() {
        recyclerViewCities?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerViewCities?.setHasFixedSize(true)
    }


    private fun fetchCitiesResourceList() {
        listCities = ArrayList()
        val cities = resources.getStringArray(R.array.cityStrArray)
        for (cityName in cities) {
            listCities.add(cityName)
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

    @ExperimentalTime
    override fun onItemClick(cityName: String) {
        val intent = Intent(this, DetailForecastActivity::class.java)
        intent.putExtra("CITY", cityName)
        intent.putExtra("UNIT", storedWeatherUnit)
        startActivity(intent)

    }

}
