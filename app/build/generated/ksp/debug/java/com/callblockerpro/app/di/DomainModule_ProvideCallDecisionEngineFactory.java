package com.callblockerpro.app.di;

import com.callblockerpro.app.domain.logic.CallDecisionEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class DomainModule_ProvideCallDecisionEngineFactory implements Factory<CallDecisionEngine> {
  @Override
  public CallDecisionEngine get() {
    return provideCallDecisionEngine();
  }

  public static DomainModule_ProvideCallDecisionEngineFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CallDecisionEngine provideCallDecisionEngine() {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideCallDecisionEngine());
  }

  private static final class InstanceHolder {
    private static final DomainModule_ProvideCallDecisionEngineFactory INSTANCE = new DomainModule_ProvideCallDecisionEngineFactory();
  }
}
