package com.callblockerpro.app.service;

import com.callblockerpro.app.domain.logic.CallDecisionEngine;
import com.callblockerpro.app.domain.logic.EmergencyNumberValidator;
import com.callblockerpro.app.domain.repository.CallLogRepository;
import com.callblockerpro.app.domain.repository.ListRepository;
import com.callblockerpro.app.domain.repository.ModeRepository;
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
public final class CallBlockingService_MembersInjector implements MembersInjector<CallBlockingService> {
  private final Provider<CallDecisionEngine> decisionEngineProvider;

  private final Provider<ListRepository> listRepositoryProvider;

  private final Provider<ModeRepository> modeRepositoryProvider;

  private final Provider<EmergencyNumberValidator> emergencyNumberValidatorProvider;

  private final Provider<CallLogRepository> callLogRepositoryProvider;

  public CallBlockingService_MembersInjector(Provider<CallDecisionEngine> decisionEngineProvider,
      Provider<ListRepository> listRepositoryProvider,
      Provider<ModeRepository> modeRepositoryProvider,
      Provider<EmergencyNumberValidator> emergencyNumberValidatorProvider,
      Provider<CallLogRepository> callLogRepositoryProvider) {
    this.decisionEngineProvider = decisionEngineProvider;
    this.listRepositoryProvider = listRepositoryProvider;
    this.modeRepositoryProvider = modeRepositoryProvider;
    this.emergencyNumberValidatorProvider = emergencyNumberValidatorProvider;
    this.callLogRepositoryProvider = callLogRepositoryProvider;
  }

  public static MembersInjector<CallBlockingService> create(
      Provider<CallDecisionEngine> decisionEngineProvider,
      Provider<ListRepository> listRepositoryProvider,
      Provider<ModeRepository> modeRepositoryProvider,
      Provider<EmergencyNumberValidator> emergencyNumberValidatorProvider,
      Provider<CallLogRepository> callLogRepositoryProvider) {
    return new CallBlockingService_MembersInjector(decisionEngineProvider, listRepositoryProvider, modeRepositoryProvider, emergencyNumberValidatorProvider, callLogRepositoryProvider);
  }

  @Override
  public void injectMembers(CallBlockingService instance) {
    injectDecisionEngine(instance, decisionEngineProvider.get());
    injectListRepository(instance, listRepositoryProvider.get());
    injectModeRepository(instance, modeRepositoryProvider.get());
    injectEmergencyNumberValidator(instance, emergencyNumberValidatorProvider.get());
    injectCallLogRepository(instance, callLogRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.callblockerpro.app.service.CallBlockingService.decisionEngine")
  public static void injectDecisionEngine(CallBlockingService instance,
      CallDecisionEngine decisionEngine) {
    instance.decisionEngine = decisionEngine;
  }

  @InjectedFieldSignature("com.callblockerpro.app.service.CallBlockingService.listRepository")
  public static void injectListRepository(CallBlockingService instance,
      ListRepository listRepository) {
    instance.listRepository = listRepository;
  }

  @InjectedFieldSignature("com.callblockerpro.app.service.CallBlockingService.modeRepository")
  public static void injectModeRepository(CallBlockingService instance,
      ModeRepository modeRepository) {
    instance.modeRepository = modeRepository;
  }

  @InjectedFieldSignature("com.callblockerpro.app.service.CallBlockingService.emergencyNumberValidator")
  public static void injectEmergencyNumberValidator(CallBlockingService instance,
      EmergencyNumberValidator emergencyNumberValidator) {
    instance.emergencyNumberValidator = emergencyNumberValidator;
  }

  @InjectedFieldSignature("com.callblockerpro.app.service.CallBlockingService.callLogRepository")
  public static void injectCallLogRepository(CallBlockingService instance,
      CallLogRepository callLogRepository) {
    instance.callLogRepository = callLogRepository;
  }
}
