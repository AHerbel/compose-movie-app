package com.aherbel.movieapp.infrastructure.remote

import com.aherbel.movieapp.infrastructure.remote.responses.MoviesResponse
import com.aherbel.movieapp.infrastructure.remote.responses.NetworkMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    
    @GET("movie/top_rated")
    suspend fun topRated(@Query("page") page: Int = 1): MoviesResponse
    
    @GET("movie/{movieId}")
    suspend fun movie(
        @Path("movieId") id: Int,
        @Query("append_to_response") append: String = "images,credits"
    ): NetworkMovie

}