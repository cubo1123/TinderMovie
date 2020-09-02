package com.cubo1123.movie.tinder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.cubo1123.movie.tinder.database.MovieStatus
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

    val movies : LiveData<List<MovieProfile>> = Transformations.map(database.movieDao.getMovies()){
        it.asDomainModel()
    }
    val myMovies : LiveData<List<MovieProfile>> = Transformations.map(database.movieDao.getMyMovies()){
        it.asDomainModel()
    }

    suspend fun refreshMovies(page : Int){
        withContext(Dispatchers.IO){
            try {
                val recentMovies = networkService.getPopularMoviesAsync(page = page).await()
                Timber.d(recentMovies.toString())
                database.movieDao.insertAllMovie(*recentMovies.asDataBaseModel())
                recentMovies.results.map { movie ->
                    database.movieDao.insertAllGenderReference(*movie.genders.asGenderReference(movie = movie.id))
                }
            }catch (e : Throwable){
                Timber.e(e)
            }

        }
    }

    suspend fun matchMovie(movieProfile: Int) {
        withContext(Dispatchers.IO){
            database.movieDao.updateMovie(status = MovieStatus.MATCH,id = movieProfile)
            val movie = database.movieDao.getMovie(movieProfile)
            Log.e("DEBUG","pelicula ${movie.movieId} ${movie.title} actualizada ${movie.isMatch}")
        }
    }

    suspend fun notMatchedMovie(movieProfile: Int){
        withContext(Dispatchers.IO){
            database.movieDao.updateMovie(status = MovieStatus.NOT_MATCHED,id = movieProfile)
            val movie = database.movieDao.getMovie(movieProfile)
            Log.e("DEBUG","pelicula ${movie.movieId} ${movie.title} actualizada ${movie.isMatch}")
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