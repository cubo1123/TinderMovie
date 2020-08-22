package com.cubo1123.movie.tinder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cubo1123.movie.tinder.R
import com.cubo1123.movie.tinder.network.Network.networkService
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}