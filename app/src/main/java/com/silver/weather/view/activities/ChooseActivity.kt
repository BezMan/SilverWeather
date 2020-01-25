package com.silver.weather.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.silver.weather.Interfaces.Search
import com.silver.weather.R
import com.silver.weather.view.fragments.ListFragment
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity(), Search {

    override fun sendData(text: String, submit: Boolean) {
        ListFragment.receiveData(text, submit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        actionBarChoose.setTitle(R.string.app_name)
        setSupportActionBar(actionBarChoose)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_choose, menu)

        val itemSearch = menu?.findItem(R.id.icSearch)
        val viewSearch = itemSearch?.actionView as android.support.v7.widget.SearchView
        viewSearch.queryHint = "Write city..."

        viewSearch.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {

            //when the user presses enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                sendData(query!!, true)
                return true
            }

            //when the text changes
            override fun onQueryTextChange(newText: String?): Boolean {
                sendData(newText!!, false)
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
