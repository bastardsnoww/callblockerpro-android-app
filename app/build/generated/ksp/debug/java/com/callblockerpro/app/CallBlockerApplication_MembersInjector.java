package com.callblockerpro.app;

import androidx.hilt.work.HiltWorkerFactory;
import com.callblockerpro.app.analytics.AnalyticsHelper;
import com.callblockerpro.app.analytics.CrashlyticsHelper;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class CallBlockerApplication_MembersInjector implements MembersInjector<CallBlockerApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<AnalyticsHelper> analyticsHelperProvider;

  private final Provider<CrashlyticsHelper> crashlyticsHelperProvider;

  public CallBlockerApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<AnalyticsHelper> analyticsHelperProvider,
      Provider<CrashlyticsHelper> crashlyticsHelperProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.analyticsHelperProvider = analyticsHelperProvider;
    this.crashlyticsHelperProvider = crashlyticsHelperProvider;
  }

  public static MembersInjector<CallBlockerApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<AnalyticsHelper> analyticsHelperProvider,
      Provider<CrashlyticsHelper> crashlyticsHelperProvider) {
    return new CallBlockerApplication_MembersInjector(workerFactoryProvider, analyticsHelperProvider, crashlyticsHelperProvider);
  }

  @Override
  public void injectMembers(CallBlockerApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectAnalyticsHelper(instance, analyticsHelperProvider.get());
    injectCrashlyticsHelper(instance, crashlyticsHelperProvider.get());
  }

  @InjectedFieldSignature("com.callblockerpro.app.CallBlockerApplication.workerFactory")
  public static void injectWorkerFactory(CallBlockerApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.callblockerpro.app.CallBlockerApplication.analyticsHelper")
  public static void injectAnalyticsHelper(CallBlockerApplication instance,
      AnalyticsHelper analyticsHelper) {
    instance.analyticsHelper = analyticsHelper;
  }

  @InjectedFieldSignature("com.callblockerpro.app.CallBlockerApplication.crashlyticsHelper")
  public static void injectCrashlyticsHelper(CallBlockerApplication instance,
      CrashlyticsHelper crashlyticsHelper) {
    instance.crashlyticsHelper = crashlyticsHelper;
  }
}
