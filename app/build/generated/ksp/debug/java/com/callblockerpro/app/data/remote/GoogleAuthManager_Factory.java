package com.callblockerpro.app.data.remote;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class GoogleAuthManager_Factory implements Factory<GoogleAuthManager> {
  private final Provider<Context> contextProvider;

  public GoogleAuthManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GoogleAuthManager get() {
    return newInstance(contextProvider.get());
  }

  public static GoogleAuthManager_Factory create(Provider<Context> contextProvider) {
    return new GoogleAuthManager_Factory(contextProvider);
  }

  public static GoogleAuthManager newInstance(Context context) {
    return new GoogleAuthManager(context);
  }
}
