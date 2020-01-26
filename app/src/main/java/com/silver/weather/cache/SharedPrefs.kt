package com.silver.weather.cache

import android.preference.PreferenceManager
import com.silver.weather.App

object SharedPrefs {

    const val UNIT_KEY = "UNIT_KEY"
    const val CELSIUS = "&units=metric"
    const val FAHRENHEIT = "&units=imperial"

    fun saveStringData(key: String, value: String) {
        setValue(key, value)
    }

    fun loadStringData(key: String): String {
        return getValue(key)
    }

    private fun setValue(key: String, value: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext())
        val prefsEditor = preferences.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    private fun getValue(key: String): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext())
        return preferences.getString(key, CELSIUS)!!
    }
}