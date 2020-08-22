package com.cubo1123.movie.tinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cubo1123.movie.tinder.network.Network.networkService
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val movies = networkService.getPopularMoviesAsync().await()
                    Timber.d(movies.toString())
                }catch (e : Throwable){
                    Timber.e(e.toString())
                }
            }
        }
    }
}