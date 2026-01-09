package com.callblockerpro.app.scheduler;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.callblockerpro.app.data.local.PreferenceManager;
import com.callblockerpro.app.domain.repository.ModeRepository;
import com.callblockerpro.app.domain.repository.ScheduleRepository;
import com.callblockerpro.app.domain.scheduler.SchedulerManager;
import dagger.internal.DaggerGenerated;
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
public final class SchedulerWorker_Factory {
  private final Provider<ScheduleRepository> scheduleRepositoryProvider;

  private final Provider<ModeRepository> modeRepositoryProvider;

  private final Provider<PreferenceManager> preferenceManagerProvider;

  private final Provider<SchedulerManager> schedulerManagerProvider;

  public SchedulerWorker_Factory(Provider<ScheduleRepository> scheduleRepositoryProvider,
      Provider<ModeRepository> modeRepositoryProvider,
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<SchedulerManager> schedulerManagerProvider) {
    this.scheduleRepositoryProvider = scheduleRepositoryProvider;
    this.modeRepositoryProvider = modeRepositoryProvider;
    this.preferenceManagerProvider = preferenceManagerProvider;
    this.schedulerManagerProvider = schedulerManagerProvider;
  }

  public SchedulerWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, scheduleRepositoryProvider.get(), modeRepositoryProvider.get(), preferenceManagerProvider.get(), schedulerManagerProvider.get());
  }

  public static SchedulerWorker_Factory create(
      Provider<ScheduleRepository> scheduleRepositoryProvider,
      Provider<ModeRepository> modeRepositoryProvider,
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<SchedulerManager> schedulerManagerProvider) {
    return new SchedulerWorker_Factory(scheduleRepositoryProvider, modeRepositoryProvider, preferenceManagerProvider, schedulerManagerProvider);
  }

  public static SchedulerWorker newInstance(Context context, WorkerParameters params,
      ScheduleRepository scheduleRepository, ModeRepository modeRepository,
      PreferenceManager preferenceManager, SchedulerManager schedulerManager) {
    return new SchedulerWorker(context, params, scheduleRepository, modeRepository, preferenceManager, schedulerManager);
  }
}
