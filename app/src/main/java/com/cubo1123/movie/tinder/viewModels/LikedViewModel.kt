package com.cubo1123.movie.tinder.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LikedViewModel(application: Application) : AndroidViewModel(application) {


    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom((LikedViewModel::class.java))){
                return LikedViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct view model ")
        }

    }
}