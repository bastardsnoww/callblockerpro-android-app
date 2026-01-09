package com.callblockerpro.app.data.repository;

import com.callblockerpro.app.data.local.dao.BackupActivityDao;
import com.callblockerpro.app.domain.repository.CallLogRepository;
import com.callblockerpro.app.domain.repository.ListRepository;
import com.callblockerpro.app.domain.repository.ScheduleRepository;
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
public final class BackupRepositoryImpl_Factory implements Factory<BackupRepositoryImpl> {
  private final Provider<ListRepository> listRepositoryProvider;

  private final Provider<CallLogRepository> callLogRepositoryProvider;

  private final Provider<ScheduleRepository> scheduleRepositoryProvider;

  private final Provider<BackupActivityDao> backupActivityDaoProvider;

  public BackupRepositoryImpl_Factory(Provider<ListRepository> listRepositoryProvider,
      Provider<CallLogRepository> callLogRepositoryProvider,
      Provider<ScheduleRepository> scheduleRepositoryProvider,
      Provider<BackupActivityDao> backupActivityDaoProvider) {
    this.listRepositoryProvider = listRepositoryProvider;
    this.callLogRepositoryProvider = callLogRepositoryProvider;
    this.scheduleRepositoryProvider = scheduleRepositoryProvider;
    this.backupActivityDaoProvider = backupActivityDaoProvider;
  }

  @Override
  public BackupRepositoryImpl get() {
    return newInstance(listRepositoryProvider.get(), callLogRepositoryProvider.get(), scheduleRepositoryProvider.get(), backupActivityDaoProvider.get());
  }

  public static BackupRepositoryImpl_Factory create(Provider<ListRepository> listRepositoryProvider,
      Provider<CallLogRepository> callLogRepositoryProvider,
      Provider<ScheduleRepository> scheduleRepositoryProvider,
      Provider<BackupActivityDao> backupActivityDaoProvider) {
    return new BackupRepositoryImpl_Factory(listRepositoryProvider, callLogRepositoryProvider, scheduleRepositoryProvider, backupActivityDaoProvider);
  }

  public static BackupRepositoryImpl newInstance(ListRepository listRepository,
      CallLogRepository callLogRepository, ScheduleRepository scheduleRepository,
      BackupActivityDao backupActivityDao) {
    return new BackupRepositoryImpl(listRepository, callLogRepository, scheduleRepository, backupActivityDao);
  }
}
