package com.aherbel.movieapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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