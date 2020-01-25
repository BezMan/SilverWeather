package com.silver.weather.Adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.silver.weather.Interfaces.ClickListener
import com.silver.weather.R
import kotlinx.android.synthetic.main.list_item_city.view.*

class CityAdapter(private var context: Context, items: ArrayList<City>, private var clickListener: ClickListener) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    private var items: ArrayList<City>? = null
    private var copyItem: ArrayList<City>? = null

    init {
        this.items = items
        this.copyItem = items
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_city, p0, false)
        return ViewHolder(view, clickListener)
    }

    fun filter(query: String) {
        if (query == null || query == "") {
            items = ArrayList(copyItem)
            notifyDataSetChanged()
            return
        } else {
            items?.clear()
            var search = query
            search = search.toLowerCase()
            for (item in copyItem!!) {
                val nombre = item.nameCity?.toLowerCase()
                if (nombre!!.contains(search)) {
                    items?.add(item)
                }
            }
            notifyDataSetChanged()
        }

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvCity.text = items?.get(position)?.nameCity
        viewHolder.tvCountry.text = items?.get(position)?.nameCountry

    }

    override fun getItemCount(): Int {
        return items!!.count()
    }

    class ViewHolder(view: View, clickListener: ClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var tvCity: TextView = view.tvCity
        var tvCountry: TextView = view.tvCountry
        var clickListener: ClickListener? = clickListener
        var cardView: CardView = view.cardView

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            clickListener?.onClick(p0!!, adapterPosition)
        }
    }
}