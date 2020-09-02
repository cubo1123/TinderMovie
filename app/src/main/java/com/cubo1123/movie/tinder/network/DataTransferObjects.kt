package com.cubo1123.movie.tinder.network

import com.cubo1123.movie.tinder.database.GendersMovieCrossRef
import com.cubo1123.movie.tinder.database.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.cubo1123.movie.tinder.database.Gender as GenderEntity

@JsonClass(generateAdapter = true)
data class ListMovieContainer(val page : String,val results: List<MovieObject>,@Json(name = "total_results")val totalResults : Int, @Json(name = "total_pages")val totalPages : Int)

@JsonClass(generateAdapter = true)
data class MovieObject(val id: Int,
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
                       val genders : List<Int>,
                       @Json(name = "vote_average")
                       val voteAverage : Double,
                       val overview : String,
                       @Json(name = "release_date")
                       val releaseDate : String,
                       val popularity : Double,
                       @Json(name = "vote_count")
                       val voteCount : Int)

@JsonClass(generateAdapter = true)
data class Gender(val id: Int,val name : String)

@JsonClass(generateAdapter = true)
data class ListGenders(@Json(name = "genres") val genders: List<Gender>)


fun ListMovieContainer.asDataBaseModel() : Array<Movie>{
    return results.map {
        Movie(movieId = it.id,
                hasVideo = it.hasVideo,
                posterUrl = it.posterUrl,
                isRType = it.isRType,
                backdropUrl = it.backdropUrl,
                language = it.language,
                title = it.title,
                voteAverage = it.voteAverage,
                overview = it.overview,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteCount = it.voteCount
        )
    }.toTypedArray()
}

fun List<Int>.asGenderReference(movie: Int): Array<GendersMovieCrossRef>{
    return map {
        GendersMovieCrossRef(movieId = movie,genderId = it)
    }.toTypedArray()
}

fun ListGenders.asDataBaseModel() : Array<GenderEntity>{
    return genders.map {
        GenderEntity(genderId = it.id,name = it.name)
    }.toTypedArray()
}