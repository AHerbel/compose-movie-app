package com.aherbel.movieapp.domain.datasources

import com.aherbel.movieapp.domain.model.Movie

interface MoviesDataSource {
    
    suspend fun getMovies(): List<Movie>
    
}