package com.silver.weather.view.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.silver.weather.R
import com.silver.weather.interfaces.IClickListener
import com.silver.weather.model.City
import kotlinx.android.synthetic.main.list_item_city.view.*

class CityListAdapter(private var context: Context, itemList: ArrayList<City>, private var IClickListener: IClickListener) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private var filteredList: ArrayList<City>? = null
    private var fullList: ArrayList<City>

    init {
        this.filteredList = itemList
        this.fullList = itemList
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_city, p0, false)
        return ViewHolder(view, IClickListener)
    }

    fun filter(query: String): ArrayList<City>? {
        if (query == "") {
            filteredList = ArrayList(fullList)
        } else {
            filteredList?.clear()
            var search = query
            search = search.toLowerCase()
            for (item in fullList) {
                val cityName = item.nameCity?.toLowerCase()
                if (cityName!!.contains(search)) {
                    filteredList?.add(item)
                }
            }
        }
        notifyDataSetChanged()
        return filteredList

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvCity.text = filteredList?.get(position)?.nameCity
        viewHolder.tvCountry.text = filteredList?.get(position)?.nameCountry

    }

    override fun getItemCount(): Int {
        return filteredList!!.count()
    }

    class ViewHolder(view: View, IClickListener: IClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var tvCity: TextView = view.tvCity
        var tvCountry: TextView = view.tvCountry
        var IClickListener: IClickListener? = IClickListener
        var cardView: CardView = view.cardView

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            IClickListener?.onClick(p0!!, adapterPosition)
        }
    }
}