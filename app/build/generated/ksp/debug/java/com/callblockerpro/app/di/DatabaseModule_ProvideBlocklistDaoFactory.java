package com.callblockerpro.app.di;

import com.callblockerpro.app.data.local.AppDatabase;
import com.callblockerpro.app.data.local.dao.BlocklistDao;
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
public final class DatabaseModule_ProvideBlocklistDaoFactory implements Factory<BlocklistDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideBlocklistDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BlocklistDao get() {
    return provideBlocklistDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBlocklistDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideBlocklistDaoFactory(dbProvider);
  }

  public static BlocklistDao provideBlocklistDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBlocklistDao(db));
  }
}
