package com.callblockerpro.app.scheduler;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SchedulerWorker_AssistedFactory_Impl implements SchedulerWorker_AssistedFactory {
  private final SchedulerWorker_Factory delegateFactory;

  SchedulerWorker_AssistedFactory_Impl(SchedulerWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public SchedulerWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<SchedulerWorker_AssistedFactory> create(
      SchedulerWorker_Factory delegateFactory) {
    return InstanceFactory.create(new SchedulerWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<SchedulerWorker_AssistedFactory> createFactoryProvider(
      SchedulerWorker_Factory delegateFactory) {
    return InstanceFactory.create(new SchedulerWorker_AssistedFactory_Impl(delegateFactory));
  }
}
