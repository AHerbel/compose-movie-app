package com.aherbel.infrastructure.di.modules

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
    abstract fun bindsMoviesRepository(localMoviesRepository: LocalMoviesRepository): MoviesRepository
    
}