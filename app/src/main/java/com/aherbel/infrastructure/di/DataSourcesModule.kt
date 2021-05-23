package com.aherbel.infrastructure.di

import com.aherbel.infrastructure.datasources.NetworkMoviesDataSource
import com.aherbel.movieapp.domain.datasources.MoviesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourcesModule {
    
    @Binds
    abstract fun bindsMoviesDataSource(networkMoviesDataSource: NetworkMoviesDataSource): MoviesDataSource
    
}