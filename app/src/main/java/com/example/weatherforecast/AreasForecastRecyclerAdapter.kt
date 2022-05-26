package com.example.weatherforecast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AreasForecastRecyclerAdapter : RecyclerView.Adapter<AreasForecastViewHolder>() {
    private val items: ArrayList<AreaData> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreasForecastViewHolder {
        return AreasForecastViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AreasForecastViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item.name, item.forecast, item.icon)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(data: ArrayList<AreaData>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}