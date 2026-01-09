package com.callblockerpro.app.domain.logic;

import com.callblockerpro.app.domain.repository.RemoteConfigRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class EmergencyNumberValidator_Factory implements Factory<EmergencyNumberValidator> {
  private final Provider<RemoteConfigRepository> remoteConfigRepositoryProvider;

  public EmergencyNumberValidator_Factory(
      Provider<RemoteConfigRepository> remoteConfigRepositoryProvider) {
    this.remoteConfigRepositoryProvider = remoteConfigRepositoryProvider;
  }

  @Override
  public EmergencyNumberValidator get() {
    return newInstance(remoteConfigRepositoryProvider.get());
  }

  public static EmergencyNumberValidator_Factory create(
      Provider<RemoteConfigRepository> remoteConfigRepositoryProvider) {
    return new EmergencyNumberValidator_Factory(remoteConfigRepositoryProvider);
  }

  public static EmergencyNumberValidator newInstance(
      RemoteConfigRepository remoteConfigRepository) {
    return new EmergencyNumberValidator(remoteConfigRepository);
  }
}
