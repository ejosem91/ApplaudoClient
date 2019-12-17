package com.example.applaudoclient.db

import android.app.Application

class ApplicationWork : Application() {

    override fun onCreate() {
        super.onCreate()
        UpdateDataBase.start(this@ApplicationWork)
    }
}