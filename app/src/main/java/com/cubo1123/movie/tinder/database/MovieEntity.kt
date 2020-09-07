package com.cubo1123.movie.tinder.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cubo1123.movie.tinder.domain.GenderDomain
import com.cubo1123.movie.tinder.domain.MovieProfile
import com.cubo1123.movie.tinder.network.CollectionObject
import com.cubo1123.movie.tinder.network.LanguageObject
import com.cubo1123.movie.tinder.network.ProductionCompanyObject
import com.cubo1123.movie.tinder.network.ProductionCountryObject
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

enum class MovieStatus{MATCH,NOT_MATCHED,WAITING}

@Entity
    data class Movie constructor(
        @PrimaryKey
        val movieId: Int,
        @ColumnInfo(name = "video")
        val hasVideo : Boolean,
        @ColumnInfo(name = "poster_path")
        val posterUrl : String?,
        @ColumnInfo(name = "adult")
        val isRType : Boolean,
        @ColumnInfo(name = "backdrop_path")
        val backdropUrl : String?,
        @ColumnInfo(name = "original_language")
        val language : String,
        @ColumnInfo(name = "original_title")
        val title : String,
        @ColumnInfo(name = "vote_average")
        val voteAverage : Double,
        val overview : String,
        @ColumnInfo(name = "release_date")
        val releaseDate : String,
        val popularity : Double,
        @ColumnInfo(name = "vote_count")
        val voteCount : Int,
        var isMatch : MovieStatus = MovieStatus.WAITING
    )

    @Entity
    data class MovieDetail(
        val adult: Boolean,
        @ColumnInfo(name = "belongs_to_collection")
        val collection : Collection,
        val budget: Long,
        val homepage: String,
        @ColumnInfo(name = "imdb_id")
        val imdbId: String,
        @ColumnInfo(name = "production_companies")
        val productionCompanies: List<ProductionCompany>,
        @ColumnInfo(name = "production_countries")
        val productionCountries: List<ProductionCountry>,
        @ColumnInfo(name = "release_date")
        val releaseDate: Date,
        val revenue: Long,
        val runtime: Int,
        @ColumnInfo(name = "spoken_languages")
        val spokenLanguageObjects: List<Language>,
        val status: String,
        val tagline: String,
        val title: String
    )

    @Entity
    data class Collection(@PrimaryKey val id: Int, val name: String, @ColumnInfo(name = "poster_path")val posterUrl: String, @ColumnInfo(name = "backdrop_path")val backdropUrl: String)

    @Entity
    data class ProductionCompany(@PrimaryKey val companyId: Int, @ColumnInfo(name = "logo_path") val logoUrl: String?, val name: String, @ColumnInfo(name = "origin_country") val originCountry: String)

    @Entity
    data class ProductionCountry(@PrimaryKey @ColumnInfo(name = "iso_3166_1")val country: String, val name: String?)

    @Entity
    data class Language(@PrimaryKey @ColumnInfo(name = "iso_639_1")val language: String, val name: String?)

    @Entity
    data class Gender(
        @PrimaryKey
        val genderId: Int,
        val name : String
    )

    @Entity(primaryKeys = ["movieId", "genderId"])
    data class GendersMovieCrossRef(
        val movieId: Int,
        val genderId: Int
    )

    @Entity(primaryKeys = ["movieId", "companyId"])
    data class CompanyMovieCrossRef(
        val movieId: Int,
        val companyId: Int
    )

    @Entity(primaryKeys = ["movieId", "country"])
    data class CountryMovieCrossRef(
        val movieId: Int,
        val country: String
    )

    @Entity(primaryKeys = ["movieId", "language"])
    data class LanguageMovieCrossRef(
        val movieId: Int,
        val language: String
    )

    data class MovieWithGenders(
        @Embedded val movie: Movie,
        @Relation(
            parentColumn = "movieId",
            entityColumn = "genderId",
            associateBy = Junction(GendersMovieCrossRef::class)
        )
        val genders: List<Gender>
    )


fun List<MovieWithGenders>.asDomainModel() : List<MovieProfile> {
    return map {
        MovieProfile(id = it.movie.movieId,
            posterUrl = it.movie.posterUrl,
            isRType = it.movie.isRType,
            language = it.movie.language,
            title = it.movie.title,
            voteAverage = it.movie.voteAverage,
            overview = it.movie.overview,
            popularity = it.movie.popularity,
            isMatch = it.movie.isMatch,
            genders = it.genders.map { gender ->  GenderDomain(id = gender.genderId, name = gender.name) }
        )
    }
}

@Dao
interface MovieDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMovie(vararg movies : Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllGenders(vararg gender : Gender)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllLanguages(vararg language: Language)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCompanies(vararg gender : Gender)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCountries(vararg countries : ProductionCountry)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllGenderReference(vararg gender : GendersMovieCrossRef)

    @Transaction
    @Query("SELECT * FROM movie where isMatch = 'WAITING' ")
    fun getMovies(): LiveData<List<MovieWithGenders>>

    @Transaction
    @Query("SELECT * FROM movie where isMatch = 'MATCH'")
    fun getMyMovies(): LiveData<List<MovieWithGenders>>

    @Query("UPDATE movie SET isMatch = :status WHERE movieId = :id")
    fun updateMovie(status: MovieStatus,id:Int)


}