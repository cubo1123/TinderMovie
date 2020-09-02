package com.cubo1123.movie.tinder

import android.app.Application
import android.content.Context
import timber.log.Timber

class MovieMatchApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }
}