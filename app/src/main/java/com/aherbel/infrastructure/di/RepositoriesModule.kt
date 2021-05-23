package com.aherbel.infrastructure.di

import com.aherbel.infrastructure.repositories.DefaultMoviesRepository
import com.aherbel.infrastructure.repositories.LocalMoviesRepository
import com.aherbel.movieapp.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {
    
    @Binds
    abstract fun bindsMoviesRepository(defaultMoviesRepository: DefaultMoviesRepository): MoviesRepository
    
}