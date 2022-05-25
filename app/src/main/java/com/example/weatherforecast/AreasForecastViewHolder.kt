package com.example.weatherforecast

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AreasForecastViewHolder(
    private val itemView: View
): RecyclerView.ViewHolder(itemView) {
    private val tvAreaName: TextView = itemView.findViewById(R.id.area_name)
    private val tvAreaWeather: TextView = itemView.findViewById(R.id.area_weather)

    fun bind(areaNameContent: String, areaWeatherContent: String) {
        tvAreaName.text = areaNameContent
        tvAreaWeather.text = areaWeatherContent
    }

    companion object {
        fun create(parent: ViewGroup): AreasForecastViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_basic_forecast, parent, false)
            return AreasForecastViewHolder(itemView)
        }
    }
}