package com.example.applaudoclient

import android.app.Application

class ApplicationWork : Application() {

    override fun onCreate() {
        super.onCreate()
        UpdateDataBase.start(this@ApplicationWork)

    }
}