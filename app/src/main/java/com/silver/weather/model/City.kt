package com.silver.weather.model

class City(nameCity: String, nameCountry: String) {
    var nameCity: String? = null
    var nameCountry: String? = null

    init {
        this.nameCity = nameCity
        this.nameCountry = nameCountry
    }
}