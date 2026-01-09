package com.callblockerpro.app.di;

import com.callblockerpro.app.domain.scheduler.SchedulerManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class SchedulerModule_ProvideSchedulerManagerFactory implements Factory<SchedulerManager> {
  @Override
  public SchedulerManager get() {
    return provideSchedulerManager();
  }

  public static SchedulerModule_ProvideSchedulerManagerFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SchedulerManager provideSchedulerManager() {
    return Preconditions.checkNotNullFromProvides(SchedulerModule.INSTANCE.provideSchedulerManager());
  }

  private static final class InstanceHolder {
    private static final SchedulerModule_ProvideSchedulerManagerFactory INSTANCE = new SchedulerModule_ProvideSchedulerManagerFactory();
  }
}
