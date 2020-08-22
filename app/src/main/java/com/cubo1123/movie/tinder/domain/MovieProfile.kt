package com.cubo1123.movie.tinder.domain

import androidx.room.ColumnInfo

data class MovieProfile(val id: Int,
                        val posterUrl : String?,
                        val isRType : Boolean,
                        val language : String,
                        val title : String,
                        //    @ColumnInfo(name = "genre_ids")
                        //    val genderId : List<Int>,
                        val voteAverage : Double,
                        val overview : String,
                        val popularity : Double,
                        val isMatch : Boolean? = null)