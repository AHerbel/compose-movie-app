package com.aherbel.movieapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {
    
    private var originalMovies: MutableList<Movie> by mutableStateOf(mutableListOf())
    
    var searchQuery: String by mutableStateOf("")
        private set
    
    var selectedMovie: Movie? by mutableStateOf(originalMovies.firstOrNull())
        private set
    
    var movies: List<Movie> by mutableStateOf(originalMovies)
        private set
    
    init {
        viewModelScope.launch {
            originalMovies = moviesRepository.getMovies().toMutableList().also {
                movies = it
            }
        }
    }
    
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