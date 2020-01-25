package com.silver.weather.Interfaces

import com.google.android.gms.location.LocationResult

interface locationListener {
    fun locationResponse(locationResult: LocationResult)
}