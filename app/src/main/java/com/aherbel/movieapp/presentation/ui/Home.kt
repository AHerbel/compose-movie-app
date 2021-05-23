package com.aherbel.movieapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aherbel.infrastructure.repositories.LocalMoviesRepository
import com.aherbel.movieapp.R
import com.aherbel.movieapp.presentation.theme.MovieAppTheme
import com.aherbel.movieapp.presentation.viewmodels.MoviesViewModel
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun Home(moviesViewModel: MoviesViewModel) {
    val carouselState = rememberCarouselState { selectedIndex ->
        moviesViewModel.onSelectedMovieChanged(selectedIndex)
    }
    
    val selectedMovie = moviesViewModel.selectedMovie
    
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberCoilPainter(request = selectedMovie?.backdropImageUrl ?: R.drawable.joker),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = ColorPainter(Color.Black.copy(alpha = 0.5f)),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
        )
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            TopMenu(moviesViewModel.searchQuery, moviesViewModel::onSearchQueryChanged)
            Spacer(Modifier.height(48.dp))
            
            Carousel(
                moviesViewModel.movies,
                carouselState,
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
        Home(moviesViewModel)
    }
}