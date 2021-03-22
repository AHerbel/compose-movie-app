package com.aherbel.movieapp.ui.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aherbel.movieapp.model.Movie

class MoviesViewModel : ViewModel() {
    
    private val originalMovies: List<Movie> by derivedStateOf { com.aherbel.movieapp.movies }
    
    var searchQuery: String by mutableStateOf("")
        private set
    
    var selectedMovie: Movie? by mutableStateOf(originalMovies.first())
        private set
    
    var movies: List<Movie> by mutableStateOf(originalMovies)
        private set
    
    fun onSearchQueryChanged(searchQuery: String) {
        this.searchQuery = searchQuery
        movies = if (searchQuery.isEmpty()) {
            originalMovies
        } else {
            originalMovies.filter { it.name.contains(searchQuery, true) }
        }
    }
    
    fun onSelectedMovieChanged(movieIndex: Int) {
        selectedMovie = if (movies.isEmpty()) {
            null
        } else {
            movies[movieIndex]
        }
    }
    
}