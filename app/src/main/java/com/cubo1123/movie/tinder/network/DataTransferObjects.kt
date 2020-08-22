package com.cubo1123.movie.tinder.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListMovieContainer(val page : String,val results: List<MovieObject>,@Json(name = "total_results")val totalResults : Int, @Json(name = "total_pages")val totalPages : Int)

@JsonClass(generateAdapter = true)
data class MovieObject(val id: Int,
                       val name : Float,
                       @Json(name = "video")
                       val hasVideo : Boolean,
                       @Json(name = "poster_path")
                       val posterUrl : String?,
                       @Json(name = "adult")
                       val isRType : Boolean,
                       @Json(name = "backdrop_path")
                       val backdropUrl : String?,
                       @Json(name = "original_language")
                       val language : String,
                       @Json(name = "original_title")
                       val title : String,
                       @Json(name = "genre_ids")
                       val genderId : List<Int>,
                       @Json(name = "vote_average")
                       val voteAverage : Double,
                       val overview : String,
                       @Json(name = "release_date")
                       val releaseDate : String,
                       val popularity : Double,
                       @Json(name = "vote_count")
                       val voteCount : Int)

