package com.cubo1123.movie.tinder.database

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun toMovieStatus(value: String) = enumValueOf<MovieStatus>(value)

    @TypeConverter
    fun fromMovieStatus(value: MovieStatus) = value.name
}