package com.callblockerpro.app.data.repository;

import com.callblockerpro.app.data.local.dao.CallLogDao;
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
public final class CallLogRepositoryImpl_Factory implements Factory<CallLogRepositoryImpl> {
  private final Provider<CallLogDao> callLogDaoProvider;

  public CallLogRepositoryImpl_Factory(Provider<CallLogDao> callLogDaoProvider) {
    this.callLogDaoProvider = callLogDaoProvider;
  }

  @Override
  public CallLogRepositoryImpl get() {
    return newInstance(callLogDaoProvider.get());
  }

  public static CallLogRepositoryImpl_Factory create(Provider<CallLogDao> callLogDaoProvider) {
    return new CallLogRepositoryImpl_Factory(callLogDaoProvider);
  }

  public static CallLogRepositoryImpl newInstance(CallLogDao callLogDao) {
    return new CallLogRepositoryImpl(callLogDao);
  }
}
