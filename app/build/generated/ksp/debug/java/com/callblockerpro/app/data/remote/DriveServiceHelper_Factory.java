package com.callblockerpro.app.data.remote;

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
public final class DriveServiceHelper_Factory implements Factory<DriveServiceHelper> {
  @Override
  public DriveServiceHelper get() {
    return newInstance();
  }

  public static DriveServiceHelper_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DriveServiceHelper newInstance() {
    return new DriveServiceHelper();
  }

  private static final class InstanceHolder {
    private static final DriveServiceHelper_Factory INSTANCE = new DriveServiceHelper_Factory();
  }
}
