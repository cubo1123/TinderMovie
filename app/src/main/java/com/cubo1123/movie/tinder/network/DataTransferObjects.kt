package com.cubo1123.movie.tinder.network

import com.cubo1123.movie.tinder.database.GendersMovieCrossRef
import com.cubo1123.movie.tinder.database.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*
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
data class CollectionObject(val id: Int, val name: String, @Json(name = "poster_path")val posterUrl: String, @Json(name = "backdrop_path")val backdropUrl: String)

@JsonClass(generateAdapter = true)
data class MovieDetailContainer(
    val adult: Boolean,
    @Json(name = "belongs_to_collection")
    val collection : CollectionObject?,
    val budget: Long,
    val homepage: String,
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompanyObject>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountryObject>,
    val revenue: Long,
    val runtime: Int,
    @Json(name = "spoken_languages")
    val spokenLanguageObjects: List<LanguageObject>,
    val status: String,
    val tagline: String,
    val title: String
)

@JsonClass(generateAdapter = true)
data class ProductionCompanyObject(val id: Int, @Json(name = "logo_path") val logoUrl: String?, val name: String, @Json(name = "origin_country") val originCountry: String)

@JsonClass(generateAdapter = true)
data class ProductionCountryObject(@Json(name = "iso_3166_1")val country: String, val name: String?)

@JsonClass(generateAdapter = true)
data class LanguageObject(@Json(name = "iso_639_1")val language: String, val name: String?)

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