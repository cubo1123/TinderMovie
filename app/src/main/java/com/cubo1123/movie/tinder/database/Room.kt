package com.cubo1123.movie.tinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Movie::class,Gender::class,GendersMovieCrossRef::class,ProductionCompany::class, ProductionCountry::class, Language::class,CompanyMovieCrossRef::class,CountryMovieCrossRef::class, LanguageMovieCrossRef::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
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