package com.cubo1123.movie.tinder.database

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class Converters {
//    @TypeConverter
//    fun listToJson(value: List<Gender>): String? {
//        val moshi = Moshi.Builder().build()
//        val listOfCardsType: Type = Types.newParameterizedType(
//            List::class.java,
//            Gender::class.java
//        )
//        val adapter: JsonAdapter<List<Gender>> = moshi.adapter<List<Gender>>(listOfCardsType)
//        return adapter.toJson(value)
//    }
//
//    @TypeConverter
//    fun jsonToList(value: String): List<Gender>? {
//        val moshi = Moshi.Builder().build()
//        val listOfCardsType: Type = Types.newParameterizedType(
//            List::class.java,
//            Gender::class.java
//        )
//        val adapter: JsonAdapter<List<Gender>> = moshi.adapter<List<Gender>>(listOfCardsType)
//        val json : List<Gender>? = adapter.fromJson(value)
//        return json
//    }
}