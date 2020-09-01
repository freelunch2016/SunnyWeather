package com.wgp.sunnyweather.utils

import android.widget.Toast
import com.wgp.sunnyweather.SunnyWeatherApplication


fun String.showToast(LENGTH:Int = Toast.LENGTH_SHORT){
    Toast.makeText(SunnyWeatherApplication.context,this,LENGTH).show()
}