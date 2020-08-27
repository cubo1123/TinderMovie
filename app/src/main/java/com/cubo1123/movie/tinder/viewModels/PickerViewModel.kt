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
import timber.log.Timber
import java.lang.IllegalArgumentException

class PickerViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()

    private val database = getDatabase(application)
    private val moviesRepository = MoviesRepository(database)

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val movies = moviesRepository.movies

    init {
        viewModelScope.launch {
            moviesRepository.refreshMovies()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PickerViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return PickerViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}