package com.cubo1123.movie.tinder.repository

import androidx.lifecycle.LiveData
import com.cubo1123.movie.tinder.database.Movie
import com.cubo1123.movie.tinder.database.MoviesDatabase
import com.cubo1123.movie.tinder.network.Network.networkService
import com.cubo1123.movie.tinder.network.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository (private val database: MoviesDatabase) {

    val movies : LiveData<List<Movie>> = database.movieDao.getMyMovies()

    suspend fun refreshMovies(){
        withContext(Dispatchers.IO){
            val recentMovies = networkService.getPopularMoviesAsync().await()
            database.movieDao.insertAll(*recentMovies.asDataBaseModel())
        }
    }
}