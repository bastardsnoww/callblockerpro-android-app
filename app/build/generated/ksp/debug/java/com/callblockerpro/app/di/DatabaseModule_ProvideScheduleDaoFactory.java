package com.callblockerpro.app.di;

import com.callblockerpro.app.data.local.AppDatabase;
import com.callblockerpro.app.data.local.dao.ScheduleDao;
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
public final class DatabaseModule_ProvideScheduleDaoFactory implements Factory<ScheduleDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideScheduleDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ScheduleDao get() {
    return provideScheduleDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideScheduleDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideScheduleDaoFactory(dbProvider);
  }

  public static ScheduleDao provideScheduleDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideScheduleDao(db));
  }
}
