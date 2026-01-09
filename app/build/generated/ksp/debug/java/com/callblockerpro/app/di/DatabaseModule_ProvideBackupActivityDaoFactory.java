package com.callblockerpro.app.di;

import com.callblockerpro.app.data.local.AppDatabase;
import com.callblockerpro.app.data.local.dao.BackupActivityDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideBackupActivityDaoFactory implements Factory<BackupActivityDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideBackupActivityDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BackupActivityDao get() {
    return provideBackupActivityDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBackupActivityDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideBackupActivityDaoFactory(dbProvider);
  }

  public static BackupActivityDao provideBackupActivityDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBackupActivityDao(db));
  }
}
