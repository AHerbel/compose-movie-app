package com.aherbel.movieapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.movies
import com.aherbel.movieapp.presentation.theme.MovieAppTheme
import com.aherbel.movieapp.presentation.theme.jokerFont
import com.aherbel.movieapp.presentation.theme.red

@Composable
fun MovieDetails(movie: Movie) {
    Column {
        Text(
            text = movie.name,
            color = Color.White,
            fontSize = 62.sp,
            fontFamily = jokerFont,
            textAlign = TextAlign.Center,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )
    }
    Row {
        if (movie.category.isNotEmpty()) {
            Category(movie.category)
        }
        Spacer(Modifier.width(8.dp))
        if (movie.ageClasification > 0) {
            AgeClasification(movie.ageClasification)
        }
        Spacer(Modifier.width(8.dp))
        Score(movie.score)
    }
    Spacer(Modifier.height(24.dp))
    
    Descriptors(
        descriptors = listOf(movie.year.toString()),
        modifier = Modifier.padding(horizontal = 8.dp)
    )
    if (movie.tags.isNotEmpty()) {
        Spacer(Modifier.height(12.dp))
        Descriptors(descriptors = movie.tags)
    }
    Spacer(Modifier.height(12.dp))
    Descriptors(descriptors = listOf("Popularity ${movie.popularity.toInt()}%"))
    if (movie.overview.isNotEmpty()) {
        Spacer(Modifier.height(12.dp))
        Descriptors(
            descriptors = listOf(movie.overview),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
    Spacer(Modifier.height(24.dp))
    Line(color = red)
    Spacer(Modifier.height(24.dp))
    BuyTicketButton()
    Spacer(Modifier.height(24.dp))
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_3A
)
@Composable
fun MovieDetailsPreview() {
    MovieAppTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            MovieDetails(movie = movies.first())
        }
    }
}