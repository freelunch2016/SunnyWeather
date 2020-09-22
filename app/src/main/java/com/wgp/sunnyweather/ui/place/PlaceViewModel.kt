package com.wgp.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wgp.sunnyweather.logic.Reposistory
import com.wgp.sunnyweather.logic.model.Place


/**
 * 通常ViewModel和Activity或Fragment是一一对应的
 */
class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Reposistory.searchPlaces(query)
    }


    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Reposistory.savePlace(place)
    fun getSavedPlace() = Reposistory.getSavedPlace()
    fun isSavedPlace() = Reposistory.isPlaceSaved()
}






