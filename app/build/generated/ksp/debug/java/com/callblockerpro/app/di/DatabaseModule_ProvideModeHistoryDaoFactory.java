package com.callblockerpro.app.di;

import com.callblockerpro.app.data.local.AppDatabase;
import com.callblockerpro.app.data.local.dao.ModeHistoryDao;
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
public final class DatabaseModule_ProvideModeHistoryDaoFactory implements Factory<ModeHistoryDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideModeHistoryDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ModeHistoryDao get() {
    return provideModeHistoryDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideModeHistoryDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideModeHistoryDaoFactory(dbProvider);
  }

  public static ModeHistoryDao provideModeHistoryDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideModeHistoryDao(db));
  }
}
