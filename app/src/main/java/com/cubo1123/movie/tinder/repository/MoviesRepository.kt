package com.cubo1123.movie.tinder.repository

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.cubo1123.movie.tinder.database.MoviesDatabase
import com.cubo1123.movie.tinder.database.asDomainModel
import com.cubo1123.movie.tinder.domain.MovieProfile
import com.cubo1123.movie.tinder.network.Network.networkService
import com.cubo1123.movie.tinder.network.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesRepository (private val database: MoviesDatabase) {

    val movies : LiveData<List<MovieProfile>> = Transformations.map(database.movieDao.getMyMovies()){
        it.asDomainModel()
    }

    suspend fun refreshMovies(){
        withContext(Dispatchers.IO){
            val recentMovies = networkService.getPopularMoviesAsync().await()
            Timber.d(recentMovies.toString())
            database.movieDao.insertAll(*recentMovies.asDataBaseModel())
        }
    }
}