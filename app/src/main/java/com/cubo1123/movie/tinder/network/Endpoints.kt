package com.cubo1123.movie.tinder.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface Endpoints{
    @GET("")
    fun getPopularMoviesAsync(): Deferred<ListMovieContainer>

}