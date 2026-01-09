package com.callblockerpro.app.data.repository;

import com.callblockerpro.app.data.local.dao.ScheduleDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ScheduleRepositoryImpl_Factory implements Factory<ScheduleRepositoryImpl> {
  private final Provider<ScheduleDao> scheduleDaoProvider;

  public ScheduleRepositoryImpl_Factory(Provider<ScheduleDao> scheduleDaoProvider) {
    this.scheduleDaoProvider = scheduleDaoProvider;
  }

  @Override
  public ScheduleRepositoryImpl get() {
    return newInstance(scheduleDaoProvider.get());
  }

  public static ScheduleRepositoryImpl_Factory create(Provider<ScheduleDao> scheduleDaoProvider) {
    return new ScheduleRepositoryImpl_Factory(scheduleDaoProvider);
  }

  public static ScheduleRepositoryImpl newInstance(ScheduleDao scheduleDao) {
    return new ScheduleRepositoryImpl(scheduleDao);
  }
}
