package com.silver.weather.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.silver.weather.R
import com.silver.weather.model.Forecast
import kotlinx.android.synthetic.main.list_item_forecast.view.*
import java.util.*

class ForecastAdapter(private var context: Context, list: ArrayList<Forecast>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var itemList: ArrayList<Forecast> = list
//    private var fullList: ArrayList<Forecast> = itemList

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast, p0, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvDate.text = itemList[position].dt_txt
        viewHolder.tvDescription.text = itemList[position].weather[0].description
        viewHolder.tvTempMax.text = "max: ${itemList[position].main.temp_max}"
        viewHolder.tvTempMin.text = "min: ${itemList[position].main.temp_min}"

        Glide.with(context).load(itemList[position].weather[0].icon).into(viewHolder.iconImg)

    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDate: TextView = view.tvDate
        var tvDescription: TextView = view.tvDescription
        var tvTempMax: TextView = view.tvTempMax
        var tvTempMin: TextView = view.tvTempMin
        var iconImg: ImageView = view.iconImg
    }

}