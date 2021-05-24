package com.aherbel.movieapp.infrastructure.repositories

import com.aherbel.movieapp.infrastructure.mappers.mapNullInputList
import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.domain.repositories.MoviesRepository
import com.aherbel.movieapp.infrastructure.mappers.NetworkMovieMapper
import com.aherbel.movieapp.infrastructure.remote.MoviesApiService
import javax.inject.Inject

class DefaultMoviesRepository @Inject constructor(
    private val moviesApiService: MoviesApiService,
    private val networkMovieMapper: NetworkMovieMapper,
) : MoviesRepository {
    
    override suspend fun getMovies(): List<Movie> {
        val response = moviesApiService.topRated()
        return mapNullInputList(response.results) {
            networkMovieMapper(it)
        }
    }
}