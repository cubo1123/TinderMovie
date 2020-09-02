package com.cubo1123.movie.tinder.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface Endpoints{
    @GET("movie/popular")
    fun getPopularMoviesAsync(@Query("api_key") order : String = "82a657d3fae0a016706fe4f2f85a514f",@Query("page") page : Int = 1): Deferred<ListMovieContainer>

    @GET("genre/movie/list")
    fun getGenderAsync(@Query("api_key") order : String = "82a657d3fae0a016706fe4f2f85a514f"): Deferred<ListGenders>
}