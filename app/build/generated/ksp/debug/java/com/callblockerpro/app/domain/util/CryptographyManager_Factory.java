package com.callblockerpro.app.domain.util;

import android.content.Context;
import com.callblockerpro.app.data.local.PreferenceManager;
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
public final class CryptographyManager_Factory implements Factory<CryptographyManager> {
  private final Provider<Context> contextProvider;

  private final Provider<PreferenceManager> preferenceManagerProvider;

  public CryptographyManager_Factory(Provider<Context> contextProvider,
      Provider<PreferenceManager> preferenceManagerProvider) {
    this.contextProvider = contextProvider;
    this.preferenceManagerProvider = preferenceManagerProvider;
  }

  @Override
  public CryptographyManager get() {
    return newInstance(contextProvider.get(), preferenceManagerProvider.get());
  }

  public static CryptographyManager_Factory create(Provider<Context> contextProvider,
      Provider<PreferenceManager> preferenceManagerProvider) {
    return new CryptographyManager_Factory(contextProvider, preferenceManagerProvider);
  }

  public static CryptographyManager newInstance(Context context,
      PreferenceManager preferenceManager) {
    return new CryptographyManager(context, preferenceManager);
  }
}
