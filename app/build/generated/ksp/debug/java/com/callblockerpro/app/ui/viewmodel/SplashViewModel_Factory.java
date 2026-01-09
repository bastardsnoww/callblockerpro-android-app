package com.callblockerpro.app.ui.viewmodel;

import com.callblockerpro.app.data.repository.UserPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public SplashViewModel_Factory(
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(userPreferencesRepositoryProvider.get());
  }

  public static SplashViewModel_Factory create(
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new SplashViewModel_Factory(userPreferencesRepositoryProvider);
  }

  public static SplashViewModel newInstance(UserPreferencesRepository userPreferencesRepository) {
    return new SplashViewModel(userPreferencesRepository);
  }
}
