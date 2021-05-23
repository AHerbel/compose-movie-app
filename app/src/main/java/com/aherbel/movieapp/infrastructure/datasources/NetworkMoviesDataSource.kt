package com.aherbel.movieapp.infrastructure.datasources

import com.aherbel.movieapp.infrastructure.mappers.mapMoviesDto
import com.aherbel.movieapp.infrastructure.remote.RetrofitClient
import com.aherbel.movieapp.domain.datasources.MoviesDataSource
import com.aherbel.movieapp.domain.mapNullInputList
import com.aherbel.movieapp.domain.model.Movie
import javax.inject.Inject

class NetworkMoviesDataSource @Inject constructor() : MoviesDataSource {
    
    override suspend fun getMovies(): List<Movie> {
        val response = RetrofitClient.moviesService.topRated()
        return mapNullInputList(response.results) {
            mapMoviesDto(it)
        }
    }
    
}