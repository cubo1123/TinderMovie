package com.cubo1123.movie.tinder.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cubo1123.movie.tinder.R
import com.cubo1123.movie.tinder.database.getDatabase
import com.cubo1123.movie.tinder.domain.MovieProfile
import com.cubo1123.movie.tinder.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PickerViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var movieToUpdate: MovieProfile
    private val viewModelJob = SupervisorJob()
    private val context = getApplication<Application>().applicationContext
    private val database = getDatabase(application)
    private val moviesRepository = MoviesRepository(database)

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _newData = true
    var newData
        get() = _newData
        set(value) {}

    val movies = moviesRepository.movies

    init {
        updateRepository()
    }

    fun updatePage(){
        val sharedPref = context.getSharedPreferences("Tinder", Context.MODE_PRIVATE)
        var page = sharedPref.getInt(context.getString(R.string.saved_page), 1)
        page += 1
        with (sharedPref.edit()) {
            putInt(context.getString(com.cubo1123.movie.tinder.R.string.saved_page), page)
            commit()
        }
    }
    fun updateRepository(){
        viewModelScope.launch {
            val sharedPref = context.getSharedPreferences("Tinder", Context.MODE_PRIVATE)
            var page = sharedPref.getInt(context.getString(R.string.saved_page), 1)
            moviesRepository.refreshGenders()
            moviesRepository.refreshMovies(page)
            _newData = true
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updatingComplete(){
        _newData = false
    }

    fun matched(){
        viewModelScope.launch {
            moviesRepository.matchMovie(movieProfile = movieToUpdate.id)
        }
    }

    fun notMatched(){
        viewModelScope.launch {
            moviesRepository.notMatchedMovie(movieProfile = movieToUpdate.id)
        }
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