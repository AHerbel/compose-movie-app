package com.aherbel.infrastructure.repositories

import com.aherbel.movieapp.domain.datasources.MoviesDataSource
import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.domain.repositories.MoviesRepository
import javax.inject.Inject

class DefaultMoviesRepository @Inject constructor(
    private val moviesDataSource: MoviesDataSource,
) : MoviesRepository {
    
    override suspend fun getMovies(): List<Movie> =
        moviesDataSource.getMovies()
}