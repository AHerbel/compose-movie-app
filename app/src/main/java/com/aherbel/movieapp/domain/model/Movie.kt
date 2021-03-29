package com.aherbel.movieapp.domain.model

data class Movie(
    val name: String,
    val year: Int,
    val genres: List<String>,
    val soundsMix: List<String>,
    val imageUrl: String,
    val score: Float,
    val ageClasification: Int,
    val category: String,
    val tags: List<String>,
)