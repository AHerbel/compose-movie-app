package com.aherbel.movieapp.infrastructure;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = MovieApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface MovieApplication_GeneratedInjector {
  void injectMovieApplication(MovieApplication movieApplication);
}
