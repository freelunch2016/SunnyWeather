package com.wgp.sunnyweather

import android.app.Application
import android.content.Context


class SunnyWeatherApplication : Application() {

    companion object{
        lateinit var context: Context
        //彩云天气令牌
        const val token = "pIe1zZfAVUuw0zpB"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
















