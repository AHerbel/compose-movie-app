package com.aherbel.movieapp.infrastructure;

import androidx.annotation.CallSuper;
import androidx.multidex.MultiDexApplication;
import dagger.hilt.android.internal.managers.ApplicationComponentManager;
import dagger.hilt.android.internal.managers.ComponentSupplier;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.UnsafeCasts;
import java.lang.Object;
import java.lang.Override;

/**
 * A generated base class to be extended by the @dagger.hilt.android.HiltAndroidApp annotated class. If using the Gradle plugin, this is swapped as the base class via bytecode transformation.
 */
public abstract class Hilt_MovieApplication extends MultiDexApplication implements GeneratedComponentManagerHolder {
  private final ApplicationComponentManager componentManager = new ApplicationComponentManager(new ComponentSupplier() {
    @Override
    public Object get() {
      return DaggerMovieApplication_HiltComponents_SingletonC.builder()
          .applicationContextModule(new ApplicationContextModule(Hilt_MovieApplication.this))
          .build();
    }
  });

  @Override
  public final ApplicationComponentManager componentManager() {
    return componentManager;
  }

  @Override
  public final Object generatedComponent() {
    return this.componentManager().generatedComponent();
  }

  @CallSuper
  @Override
  public void onCreate() {
    // This is a known unsafe cast, but is safe in the only correct use case:
    // MovieApplication extends Hilt_MovieApplication
    ((MovieApplication_GeneratedInjector) generatedComponent()).injectMovieApplication(UnsafeCasts.<MovieApplication>unsafeCast(this));
    super.onCreate();
  }
}
