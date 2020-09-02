package com.cubo1123.movie.tinder.domain


data class MovieProfile(val id: Int = -1,
                        val posterUrl : String? = "",
                        val isRType : Boolean = false,
                        val language : String = "",
                        val title : String = "",
                        val voteAverage : Double = 0.0,
                        val overview : String = "",
                        val popularity : Double = 0.0,
                        val isMatch : Boolean = false,
                        val genders : List<GenderDomain>)

data class GenderDomain(val id: Int = -1, val name : String = "")

fun List<MovieProfile>.myMovies(): List<MovieProfile> {
    return this.filter { it.isMatch }
}