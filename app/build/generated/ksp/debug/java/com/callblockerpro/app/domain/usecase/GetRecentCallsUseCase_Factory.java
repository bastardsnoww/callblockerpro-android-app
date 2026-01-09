package com.callblockerpro.app.domain.usecase;

import com.callblockerpro.app.domain.repository.CallLogRepository;
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
public final class GetRecentCallsUseCase_Factory implements Factory<GetRecentCallsUseCase> {
  private final Provider<CallLogRepository> callLogRepositoryProvider;

  public GetRecentCallsUseCase_Factory(Provider<CallLogRepository> callLogRepositoryProvider) {
    this.callLogRepositoryProvider = callLogRepositoryProvider;
  }

  @Override
  public GetRecentCallsUseCase get() {
    return newInstance(callLogRepositoryProvider.get());
  }

  public static GetRecentCallsUseCase_Factory create(
      Provider<CallLogRepository> callLogRepositoryProvider) {
    return new GetRecentCallsUseCase_Factory(callLogRepositoryProvider);
  }

  public static GetRecentCallsUseCase newInstance(CallLogRepository callLogRepository) {
    return new GetRecentCallsUseCase(callLogRepository);
  }
}
