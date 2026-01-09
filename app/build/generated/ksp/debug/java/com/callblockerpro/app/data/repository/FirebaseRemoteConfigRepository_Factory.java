package com.callblockerpro.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class FirebaseRemoteConfigRepository_Factory implements Factory<FirebaseRemoteConfigRepository> {
  @Override
  public FirebaseRemoteConfigRepository get() {
    return newInstance();
  }

  public static FirebaseRemoteConfigRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseRemoteConfigRepository newInstance() {
    return new FirebaseRemoteConfigRepository();
  }

  private static final class InstanceHolder {
    private static final FirebaseRemoteConfigRepository_Factory INSTANCE = new FirebaseRemoteConfigRepository_Factory();
  }
}
