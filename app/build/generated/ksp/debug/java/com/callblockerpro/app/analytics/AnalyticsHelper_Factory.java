package com.callblockerpro.app.analytics;

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
public final class AnalyticsHelper_Factory implements Factory<AnalyticsHelper> {
  @Override
  public AnalyticsHelper get() {
    return newInstance();
  }

  public static AnalyticsHelper_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AnalyticsHelper newInstance() {
    return new AnalyticsHelper();
  }

  private static final class InstanceHolder {
    private static final AnalyticsHelper_Factory INSTANCE = new AnalyticsHelper_Factory();
  }
}
