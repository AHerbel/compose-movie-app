package com.aherbel.movieapp.infrastructure.repositories

import com.aherbel.movieapp.domain.model.Movie
import com.aherbel.movieapp.domain.repositories.MoviesRepository
import com.aherbel.movieapp.infrastructure.mappers.NetworkMovieMapper
import com.aherbel.movieapp.infrastructure.mappers.mapNullInputList
import com.aherbel.movieapp.infrastructure.remote.MoviesApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMoviesRepository @Inject constructor(
    private val moviesApiService: MoviesApiService,
    private val networkMovieMapper: NetworkMovieMapper,
) : MoviesRepository {
    
    override fun getMovies(): Flow<List<Movie>> = flow {
        emit(
            mapNullInputList(moviesApiService.topRated().results) {
                networkMovieMapper(it)
            }
        )
    }
}