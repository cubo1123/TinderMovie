package com.cubo1123.movie.tinder.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Movie constructor(
    @PrimaryKey
    val id: Int,
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
//    @ColumnInfo(name = "genre_ids")
//    val genderId : List<Int>,
    @ColumnInfo(name = "vote_average")
    val voteAverage : Double,
    val overview : String,
    @ColumnInfo(name = "release_date")
    val releaseDate : String,
    val popularity : Double,
    @ColumnInfo(name = "vote_count")
    val voteCount : Int,
    val isMatch : Boolean? = null
)

@Dao
interface MovieDao{
    @Query("select * from movie where isMatch")
    fun getMyMovies() : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg movies : Movie)
}