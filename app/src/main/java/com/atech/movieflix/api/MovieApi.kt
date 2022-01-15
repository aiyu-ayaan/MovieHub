package com.atech.movieflix.api

import com.atech.movieflix.data.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    companion object {
        const val BASE_URL = "https://api.tvmaze.com/search/"
    }

    @GET("shows")
    suspend fun getMovies(@Query("q") query: String): List<Movie>
}