package com.cubo1123.movie.tinder.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cubo1123.movie.tinder.database.getDatabase
import com.cubo1123.movie.tinder.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MovieProfileViewModel(application: Application,val movieId: Int) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val database = getDatabase(application)
    private val moviesRepository = MoviesRepository(database=database)
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun updateMovie(){
        viewModelScope.launch {
            moviesRepository.getMovieDetails(movieId)
        }
    }
    class Factory(private val application: Application, private val movieId: Int) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieProfileViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MovieProfileViewModel(application,movieId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}