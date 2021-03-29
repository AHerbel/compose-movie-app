package com.aherbel.infrastructure.repositories

import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.domain.repositories.MoviesRepository
import com.aherbel.movieapp.movies
import javax.inject.Inject

class LocalMoviesRepository @Inject constructor(): MoviesRepository {
    
    override suspend fun getMovies(): List<Movie> {
        return movies
    }
}