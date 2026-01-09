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
public final class GetDashboardStatsUseCase_Factory implements Factory<GetDashboardStatsUseCase> {
  private final Provider<CallLogRepository> callLogRepositoryProvider;

  public GetDashboardStatsUseCase_Factory(Provider<CallLogRepository> callLogRepositoryProvider) {
    this.callLogRepositoryProvider = callLogRepositoryProvider;
  }

  @Override
  public GetDashboardStatsUseCase get() {
    return newInstance(callLogRepositoryProvider.get());
  }

  public static GetDashboardStatsUseCase_Factory create(
      Provider<CallLogRepository> callLogRepositoryProvider) {
    return new GetDashboardStatsUseCase_Factory(callLogRepositoryProvider);
  }

  public static GetDashboardStatsUseCase newInstance(CallLogRepository callLogRepository) {
    return new GetDashboardStatsUseCase(callLogRepository);
  }
}
