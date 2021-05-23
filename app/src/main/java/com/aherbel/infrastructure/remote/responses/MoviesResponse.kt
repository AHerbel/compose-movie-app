package com.aherbel.infrastructure.remote.responses

import com.squareup.moshi.Json

data class MoviesResponse(
    val page: Int,
    val results: List<NetworkMovie>?,
)

data class NetworkMovie(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "adults") val adults: Boolean?,
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "release_date") val releaseDate: String?,
    @field:Json(name = "popularity") val popularity: Float?,
    @field:Json(name = "vote_count") val voteCount: Int?,
    @field:Json(name = "vote_average") val voteAverage: Float?,
    @field:Json(name = "overview") val overview: String?,
)