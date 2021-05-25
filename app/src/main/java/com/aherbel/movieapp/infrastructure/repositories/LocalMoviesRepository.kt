package com.aherbel.movieapp.infrastructure.repositories

import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.domain.repositories.MoviesRepository
import com.aherbel.movieapp.movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalMoviesRepository @Inject constructor() : MoviesRepository {
    
    override fun getMovies(): Flow<List<Movie>> = flow {
        emit(movies)
    }
}