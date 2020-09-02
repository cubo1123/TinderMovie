package com.cubo1123.movie.tinder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.cubo1123.movie.tinder.database.GendersMovieCrossRef
import com.cubo1123.movie.tinder.database.MoviesDatabase
import com.cubo1123.movie.tinder.database.asDomainModel
import com.cubo1123.movie.tinder.domain.MovieProfile
import com.cubo1123.movie.tinder.network.Network.networkService
import com.cubo1123.movie.tinder.network.asDataBaseModel
import com.cubo1123.movie.tinder.network.asGenderReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesRepository (private val database: MoviesDatabase) {

    val movies : LiveData<List<MovieProfile>> = Transformations.map(database.movieDao.getMoviesWithGender()){
        it.asDomainModel()
    }

    suspend fun refreshMovies(){
        withContext(Dispatchers.IO){
            try {
                val recentMovies = networkService.getPopularMoviesAsync().await()
                Timber.d(recentMovies.toString())
                database.movieDao.insertAllMovie(*recentMovies.asDataBaseModel())
                recentMovies.results.map { movie ->
                    database.movieDao.insertGenderReference(*movie.genders.asGenderReference(movie = movie.id))
                }
            }catch (e : Throwable){
                Timber.e(e)
            }

        }
    }

    suspend fun refreshGenders(){
        withContext(Dispatchers.IO){
            try {
                val listGenders = networkService.getGenderAsync().await()
                Timber.d(listGenders.toString())
                database.movieDao.insertAllGenders(*listGenders.asDataBaseModel())
            }catch (e : Throwable){
                Timber.e(e)
            }
        }
    }
}