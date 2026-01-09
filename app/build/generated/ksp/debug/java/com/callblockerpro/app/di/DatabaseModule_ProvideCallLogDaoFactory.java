package com.callblockerpro.app.di;

import com.callblockerpro.app.data.local.AppDatabase;
import com.callblockerpro.app.data.local.dao.CallLogDao;
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
public final class DatabaseModule_ProvideCallLogDaoFactory implements Factory<CallLogDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCallLogDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CallLogDao get() {
    return provideCallLogDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCallLogDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCallLogDaoFactory(dbProvider);
  }

  public static CallLogDao provideCallLogDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCallLogDao(db));
  }
}
