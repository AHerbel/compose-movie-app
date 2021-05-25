package com.aherbel.movieapp.domain.repositories

import com.aherbel.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    
    fun getMovies(): Flow<List<Movie>>
    
}