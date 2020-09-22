package com.wgp.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.wgp.sunnyweather.SunnyWeatherApplication
import com.wgp.sunnyweather.logic.model.Place


object PlaceDao {

    fun savePlace(place: Place){
        sharedPreference().edit{
            putString("place",Gson().toJson(place))
        }
    }

    fun getSavedPlace():Place{
        val place = sharedPreference().getString("place", "")
        return Gson().fromJson(place,Place::class.java)
    }

    fun isPlaceSaved() = sharedPreference().contains("place")

    private fun sharedPreference() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)
}