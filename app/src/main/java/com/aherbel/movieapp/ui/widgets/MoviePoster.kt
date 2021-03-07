package com.aherbel.movieapp.ui.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aherbel.movieapp.model.Movie
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun MoviePoster(movie: Movie, modifier: Modifier = Modifier) {
    CoilImage(
        data = movie.imageUrl,
        contentDescription = null,
        modifier = modifier
    )
}