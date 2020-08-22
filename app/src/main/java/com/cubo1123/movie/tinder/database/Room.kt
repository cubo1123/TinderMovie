package com.cubo1123.movie.tinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class],version = 1,exportSchema = false)
abstract class MoviesDatabase : RoomDatabase(){
    abstract val movieDao: MovieDao
}

private lateinit var INSTANCE : MoviesDatabase

fun getDatabase(context : Context) : MoviesDatabase{
    synchronized(MoviesDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,MoviesDatabase::class.java,"movieMatch").build()
        }
        return INSTANCE
    }
}