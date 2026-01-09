package com.callblockerpro.app;

import androidx.hilt.work.HiltWorkerFactory;
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

  public CallBlockerApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<CallBlockerApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new CallBlockerApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(CallBlockerApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.callblockerpro.app.CallBlockerApplication.workerFactory")
  public static void injectWorkerFactory(CallBlockerApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
