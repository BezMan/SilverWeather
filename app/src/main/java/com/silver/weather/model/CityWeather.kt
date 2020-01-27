package com.silver.weather.model

data class CityWeather(
        val base: String,
        val clouds: Clouds,
        val cod: Int,
        val coord: Coord,
        val dt: Int,
        val id: Int,
        val main: Main,
        val name: String,
        val sys: Sys,
        val weather: List<Weather>,
        val wind: Wind
)


data class CityForecast(
        val city: CityX,
        val cnt: Int,
        val cod: String,
        val list: List<Forecast>,
        val message: Double
)


data class CityX(
        val coord: Coord,
        val country: String,
        val id: Int,
        val name: String
)

data class Coord(
        val lat: Double,
        val lon: Double
)

data class Forecast(
        val clouds: Clouds,
        val dt: Int,
        val dt_txt: String,
        val main: Main,
        val snow: Snow,
        val sys: Sys,
        val weather: List<Weather>,
        val wind: Wind
)

data class Clouds(
        val all: Int
)

data class Main(
        val grnd_level: Double,
        val humidity: Double,
        val pressure: Double,
        val sea_level: Double,
        val temp: Double,
        val temp_kf: Double,
        val temp_max: Double,
        val temp_min: Double
)

class Snow

data class Sys(
        val pod: String
)

data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
)

data class Wind(
        val deg: Double,
        val speed: Double
)