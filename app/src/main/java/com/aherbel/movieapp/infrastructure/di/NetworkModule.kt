package com.aherbel.movieapp.infrastructure.di

import com.aherbel.movieapp.infrastructure.mappers.NetworkMovieMapper
import com.aherbel.movieapp.infrastructure.mappers.mapNetworkMovie
import com.aherbel.movieapp.infrastructure.remote.MoviesApiService
import com.aherbel.movieapp.infrastructure.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {
    
    @Provides
    fun providesMoviesService(): MoviesApiService = RetrofitClient.moviesApiService
    
    @Provides
    fun providesNetworkMovieMapper(): NetworkMovieMapper = ::mapNetworkMovie
    
}