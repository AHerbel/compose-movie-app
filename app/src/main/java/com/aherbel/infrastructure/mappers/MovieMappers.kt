package com.aherbel.infrastructure.mappers

import com.aherbel.infrastructure.remote.responses.NetworkMovie
import com.aherbel.movieapp.domain.model.Movie
import java.time.LocalDate

fun mapMoviesDto(input: NetworkMovie): Movie {
    return Movie(
        name = input.title.orEmpty(),
        score = input.voteAverage ?: 0f,
        genres = emptyList(),
        soundsMix = emptyList(),
        category = "",
        tags = if (input.adults == true) listOf("ADULTS") else emptyList(),
        ageClasification = 0,
        year = yearFromDate(input.releaseDate),
        imageUrl = "https://image.tmdb.org/t/p/w780${input.posterPath}",
        backdropImageUrl = "https://image.tmdb.org/t/p/w780${input.backdropPath}",
        popularity = input.popularity ?: 0f,
        overview = input.overview.orEmpty()
    )
}

private fun yearFromDate(date: String?): Int {
    return date?.let {
        LocalDate.parse(it).year
    } ?: Int.MIN_VALUE
}