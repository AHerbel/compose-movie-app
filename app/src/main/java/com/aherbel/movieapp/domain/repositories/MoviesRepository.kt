package com.aherbel.movieapp.domain.repositories

import com.aherbel.movieapp.domain.model.Movie

interface MoviesRepository {
    
    suspend fun getMovies(): List<Movie>
    
}