package com.aherbel.movieapp.presentation.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aherbel.movieapp.domain.model.Movie
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun MoviePoster(movie: Movie, modifier: Modifier = Modifier) {
    CoilImage(
        data = movie.imageUrl,
        contentDescription = null,
        modifier = modifier
    )
}