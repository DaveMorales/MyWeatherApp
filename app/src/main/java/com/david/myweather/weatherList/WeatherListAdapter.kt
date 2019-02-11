package com.david.myweather.weatherList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.david.myweather.weatherList.WeatherListAdapter.WeatherListViewHolder
import com.david.myweather.R
import com.david.myweather.data.ResponseDto
import java.util.ArrayList


class WeatherListAdapter(cities: List<ResponseDto>?) : RecyclerView.Adapter<WeatherListViewHolder>() {

    private var weatherList = ArrayList<ResponseDto>()

    init {
        this.weatherList = cities as ArrayList<ResponseDto>
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): WeatherListViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(
                R.layout.weather_list_item,
                parent, false)
        return WeatherListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        val weatherListItem = weatherList[position]
        holder?.weatherListItem(weatherListItem)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    fun aadCityWeather(cities: List<ResponseDto>) {
        val initPosition = weatherList.size
        weatherList.clear()
        notifyItemRangeRemoved(0,initPosition)

        weatherList.addAll(cities)
        notifyItemRangeInserted(0,weatherList.size)
    }

    class WeatherListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtCity = itemView.findViewById<TextView>(R.id.txt_city)
        var txtTemperature = itemView.findViewById<TextView>(R.id.txt_temperature)
        var txtTemperatureMinMax = itemView.findViewById<TextView>(R.id.txt_temperature_min_max)
        var txtWeather = itemView.findViewById<TextView>(R.id.txt_weather)
        var imgWeather = itemView.findViewById<ImageView>(R.id.img_weather)

        fun weatherListItem(weatherItem: ResponseDto) {
            txtCity.text = weatherItem.name
            txtTemperature.text = weatherItem.main.temp.toString().plus("°")
            txtTemperatureMinMax.text = weatherItem.main.tempMin.toString()
                                            .plus("°/")
                                            .plus(weatherItem.main.tempMax)
                                            .plus("°")
            txtWeather.text = weatherItem.weather.get(0)?.main.toString()
        }
    }
}
