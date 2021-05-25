package com.aherbel.movieapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aherbel.movieapp.presentation.theme.MovieAppTheme
import com.aherbel.movieapp.presentation.ui.Home
import com.aherbel.movieapp.presentation.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private val moviesViewModel: MoviesViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val movies by moviesViewModel.movies.collectAsState()
                    val selectedMovie by moviesViewModel.selectedMovie.collectAsState()
                    val searchQuery by moviesViewModel.searchQuery.collectAsState()
                    Home(
                        movies,
                        selectedMovie,
                        searchQuery,
                        moviesViewModel::onSearchQueryChanged,
                        moviesViewModel::onSelectedMovieChanged
                    )
                }
            }
        }
    }
}
