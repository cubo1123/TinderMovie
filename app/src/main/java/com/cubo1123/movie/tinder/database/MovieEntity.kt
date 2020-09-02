package com.cubo1123.movie.tinder.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cubo1123.movie.tinder.domain.GenderDomain
import com.cubo1123.movie.tinder.domain.MovieProfile

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
    val isMatch : Boolean = false
)

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

    data class MovieWithGenders(
        @Embedded val movie: Movie,
        @Relation(
            parentColumn = "movieId",
            entityColumn = "genderId",
            associateBy = Junction(GendersMovieCrossRef::class)
        )
        val genders: List<Gender>
    )

//fun List<Movie>.asDomainModel() : List<MovieProfile> {
//    return map {
//        MovieProfile(id = it.movieId,
//                posterUrl = it.posterUrl,
//                isRType = it.isRType,
//                language = it.language,
//                title = it.title,
//                voteAverage = it.voteAverage,
//                overview = it.overview,
//                popularity = it.popularity,
//                isMatch = it.isMatch
//            )
//    }
//}

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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovie(vararg movies : Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenders(vararg gender : Gender)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenderReference(vararg gender : GendersMovieCrossRef)

    @Transaction
    @Query("SELECT * FROM movie where isMatch = 0")
    fun getMoviesWithGender(): LiveData<List<MovieWithGenders>>



}