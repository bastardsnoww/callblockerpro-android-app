package com.callblockerpro.app.data.repository;

import com.callblockerpro.app.data.local.PreferenceManager;
import com.callblockerpro.app.data.local.dao.ModeHistoryDao;
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
public final class ModeRepositoryImpl_Factory implements Factory<ModeRepositoryImpl> {
  private final Provider<PreferenceManager> preferenceManagerProvider;

  private final Provider<ModeHistoryDao> modeHistoryDaoProvider;

  public ModeRepositoryImpl_Factory(Provider<PreferenceManager> preferenceManagerProvider,
      Provider<ModeHistoryDao> modeHistoryDaoProvider) {
    this.preferenceManagerProvider = preferenceManagerProvider;
    this.modeHistoryDaoProvider = modeHistoryDaoProvider;
  }

  @Override
  public ModeRepositoryImpl get() {
    return newInstance(preferenceManagerProvider.get(), modeHistoryDaoProvider.get());
  }

  public static ModeRepositoryImpl_Factory create(
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<ModeHistoryDao> modeHistoryDaoProvider) {
    return new ModeRepositoryImpl_Factory(preferenceManagerProvider, modeHistoryDaoProvider);
  }

  public static ModeRepositoryImpl newInstance(PreferenceManager preferenceManager,
      ModeHistoryDao modeHistoryDao) {
    return new ModeRepositoryImpl(preferenceManager, modeHistoryDao);
  }
}
