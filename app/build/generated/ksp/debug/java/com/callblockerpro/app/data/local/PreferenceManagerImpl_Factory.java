package com.callblockerpro.app.data.local;

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
public final class PreferenceManagerImpl_Factory implements Factory<PreferenceManagerImpl> {
  private final Provider<Context> contextProvider;

  public PreferenceManagerImpl_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public PreferenceManagerImpl get() {
    return newInstance(contextProvider.get());
  }

  public static PreferenceManagerImpl_Factory create(Provider<Context> contextProvider) {
    return new PreferenceManagerImpl_Factory(contextProvider);
  }

  public static PreferenceManagerImpl newInstance(Context context) {
    return new PreferenceManagerImpl(context);
  }
}
