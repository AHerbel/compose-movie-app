package com.aherbel.movieapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    moviesRepository: MoviesRepository,
) : ViewModel() {
    
    private val originalMovies = moviesRepository.getMovies().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2000),
        emptyList()
    )
    private val selectedMovieIndex: MutableStateFlow<Int?> = MutableStateFlow(null)
    
    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    
    val movies: StateFlow<List<Movie>> = originalMovies.combine(searchQuery) { originalMovies, searchQuery ->
        if (searchQuery.isEmpty()) {
            originalMovies
        } else {
            originalMovies.filter { it.name.contains(searchQuery, true) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2000),
        emptyList()
    )
    
    val selectedMovie: StateFlow<Movie?> = movies.combine(selectedMovieIndex) { movies, selectedMovieIndex ->
        if (movies.isEmpty() || selectedMovieIndex == null) {
            null
        } else {
            movies[selectedMovieIndex]
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2000),
        null
    )
    
    init {
        viewModelScope.launch {
            originalMovies.collect()
        }
    }
    
    fun onSearchQueryChanged(searchQuery: String) {
        _searchQuery.value = searchQuery
    }
    
    fun onSelectedMovieChanged(movieIndex: Int) {
        selectedMovieIndex.value = movieIndex
    }
    
}