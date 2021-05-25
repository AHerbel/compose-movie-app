package com.aherbel.movieapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aherbel.movieapp.R
import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.infrastructure.repositories.LocalMoviesRepository
import com.aherbel.movieapp.presentation.theme.MovieAppTheme
import com.aherbel.movieapp.presentation.viewmodels.MoviesViewModel

@Composable
fun Home(
    movies: List<Movie>,
    selectedMovie: Movie?,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSelectedMovieChange: (Int) -> Unit
) {
    
    Box(modifier = Modifier.fillMaxSize()) {
        AlphaBackground(
            selectedMovie?.backdropImageUrl ?: R.drawable.joker,
            Color.Black,
            0.5f
        )
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            TopMenu(searchQuery, onSearchChange)
            Spacer(Modifier.height(48.dp))
    
            Carousel(
                movies,
                rememberCarouselState { selectedIndex ->
                    onSelectedMovieChange(selectedIndex)
                },
                getCarouselConfig(LocalConfiguration.current.screenWidthDp.dp)
            ) { movie ->
                MoviePoster(movie)
            }
            Spacer(Modifier.height(24.dp))
            
            if (selectedMovie != null) {
                MovieDetails(movie = selectedMovie)
            } else {
                EmptyResults()
            }
        }
    }
}

private fun getCarouselConfig(screenWidth: Dp): CarouselConfig {
    return CarouselConfig(
        itemSpacing = screenWidth * .55f,
        contentPadding = screenWidth * 0.25f
    )
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_3A
)
@Composable
fun HomePreview() {
    MovieAppTheme {
        val moviesViewModel = MoviesViewModel(LocalMoviesRepository())
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