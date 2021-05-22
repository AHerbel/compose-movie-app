package com.aherbel.movieapp.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aherbel.movieapp.domain.model.Movie
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun MoviePoster(movie: Movie, modifier: Modifier = Modifier) {
    Image(
        painter = rememberCoilPainter(request = movie.imageUrl),
        contentDescription = null,
        modifier = modifier
    )
}