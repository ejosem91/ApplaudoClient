package com.example.applaudoclient

import android.app.Application

class AplicationWork: Application() {

    override fun onCreate() {
        super.onCreate()
        UpdateDataBase.start(this@AplicationWork)

    }
}