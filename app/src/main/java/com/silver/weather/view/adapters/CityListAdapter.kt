package com.silver.weather.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.silver.weather.R
import com.silver.weather.interfaces.IClickListener
import com.silver.weather.model.CityObj
import kotlinx.android.synthetic.main.list_item_city.view.*

class CityListAdapter(private var context: Context, itemList: ArrayList<CityObj>, private var IClickListener: IClickListener) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private var filteredList: ArrayList<CityObj> = itemList
    private var fullList: ArrayList<CityObj> = itemList

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_city, p0, false)
        return ViewHolder(view, IClickListener)
    }

    fun filter(query: String) {
        if (query == "") {
            filteredList = ArrayList(fullList)
        } else {
            filteredList.clear()
            var search = query
            search = search.toLowerCase()
            for (item in fullList) {
                val cityName = item.nameCity.toLowerCase()
                if (cityName.contains(search)) {
                    filteredList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvCity.text = filteredList[position].nameCity
        viewHolder.tvDescription.text = filteredList[position].description
        viewHolder.tvTempMax.text = "max: ${filteredList[position].tempMax}"
        viewHolder.tvTempMin.text = "min: ${filteredList[position].tempMin}"

        Glide.with(context).load(filteredList[position].urlImage).into(viewHolder.iconImg)

    }

    override fun getItemCount(): Int {
        return filteredList.count()
    }

    class ViewHolder(view: View, clickListener: IClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var tvCity: TextView = view.tvCity
        var tvDescription: TextView = view.tvDescription
        var tvTempMax: TextView = view.tvTempMax
        var tvTempMin: TextView = view.tvTempMin
        var iconImg: ImageView = view.iconImg
        private var listener: IClickListener? = clickListener
        private var cardView: CardView = view.cardView

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener?.onClick(p0!!, adapterPosition)
        }
    }
}