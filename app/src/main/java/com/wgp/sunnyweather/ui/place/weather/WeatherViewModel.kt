package com.wgp.sunnyweather.ui.place.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wgp.sunnyweather.logic.Reposistory
import com.wgp.sunnyweather.logic.model.Location


class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLivedata = Transformations.switchMap(locationLiveData){ location ->
        Reposistory.refreshWeather(location.lng,location.lat)
    }
    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value = Location(lng,lat)
    }
}