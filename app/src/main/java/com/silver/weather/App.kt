package com.silver.weather

import android.app.Application
import android.content.Context

class App : Application() {


    companion object {
        private var context: Context? = null

        fun getAppContext(): Context? {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}