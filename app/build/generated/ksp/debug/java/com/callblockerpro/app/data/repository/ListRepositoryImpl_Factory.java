package com.callblockerpro.app.data.repository;

import com.callblockerpro.app.data.local.dao.BlocklistDao;
import com.callblockerpro.app.data.local.dao.WhitelistDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ListRepositoryImpl_Factory implements Factory<ListRepositoryImpl> {
  private final Provider<WhitelistDao> whitelistDaoProvider;

  private final Provider<BlocklistDao> blocklistDaoProvider;

  public ListRepositoryImpl_Factory(Provider<WhitelistDao> whitelistDaoProvider,
      Provider<BlocklistDao> blocklistDaoProvider) {
    this.whitelistDaoProvider = whitelistDaoProvider;
    this.blocklistDaoProvider = blocklistDaoProvider;
  }

  @Override
  public ListRepositoryImpl get() {
    return newInstance(whitelistDaoProvider.get(), blocklistDaoProvider.get());
  }

  public static ListRepositoryImpl_Factory create(Provider<WhitelistDao> whitelistDaoProvider,
      Provider<BlocklistDao> blocklistDaoProvider) {
    return new ListRepositoryImpl_Factory(whitelistDaoProvider, blocklistDaoProvider);
  }

  public static ListRepositoryImpl newInstance(WhitelistDao whitelistDao,
      BlocklistDao blocklistDao) {
    return new ListRepositoryImpl(whitelistDao, blocklistDao);
  }
}
