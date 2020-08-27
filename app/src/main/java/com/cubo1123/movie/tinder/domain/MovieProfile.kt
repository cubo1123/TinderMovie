package com.cubo1123.movie.tinder.domain

import androidx.room.ColumnInfo

data class MovieProfile(val id: Int = -1,
                        val posterUrl : String? = "",
                        val isRType : Boolean = false,
                        val language : String = "",
                        val title : String = "",
                        //    @ColumnInfo(name = "genre_ids")
                        //    val genderId : List<Int>,
                        val voteAverage : Double = 0.0,
                        val overview : String = "",
                        val popularity : Double = 0.0,
                        val isMatch : Boolean = false)

fun List<MovieProfile>.myMovies(): List<MovieProfile> {
    return this.filter { it.isMatch }
}