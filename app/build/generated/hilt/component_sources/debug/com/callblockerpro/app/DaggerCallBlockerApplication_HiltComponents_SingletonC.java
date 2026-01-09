package com.callblockerpro.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.callblockerpro.app.data.local.AppDatabase;
import com.callblockerpro.app.data.local.PreferenceManagerImpl;
import com.callblockerpro.app.data.local.dao.BlocklistDao;
import com.callblockerpro.app.data.local.dao.CallLogDao;
import com.callblockerpro.app.data.local.dao.ModeHistoryDao;
import com.callblockerpro.app.data.local.dao.ScheduleDao;
import com.callblockerpro.app.data.local.dao.WhitelistDao;
import com.callblockerpro.app.data.repository.CallLogRepositoryImpl;
import com.callblockerpro.app.data.repository.ListRepositoryImpl;
import com.callblockerpro.app.data.repository.ModeRepositoryImpl;
import com.callblockerpro.app.data.repository.PrivacyRemoteConfigRepository;
import com.callblockerpro.app.data.repository.ScheduleRepositoryImpl;
import com.callblockerpro.app.data.repository.UserPreferencesRepository;
import com.callblockerpro.app.di.DatabaseModule_ProvideAppDatabaseFactory;
import com.callblockerpro.app.di.DatabaseModule_ProvideBlocklistDaoFactory;
import com.callblockerpro.app.di.DatabaseModule_ProvideCallLogDaoFactory;
import com.callblockerpro.app.di.DatabaseModule_ProvideModeHistoryDaoFactory;
import com.callblockerpro.app.di.DatabaseModule_ProvideScheduleDaoFactory;
import com.callblockerpro.app.di.DatabaseModule_ProvideWhitelistDaoFactory;
import com.callblockerpro.app.di.DomainModule_ProvideCallDecisionEngineFactory;
import com.callblockerpro.app.di.SchedulerModule_ProvideSchedulerManagerFactory;
import com.callblockerpro.app.domain.logic.CallDecisionEngine;
import com.callblockerpro.app.domain.logic.EmergencyNumberValidator;
import com.callblockerpro.app.domain.repository.CallLogRepository;
import com.callblockerpro.app.domain.repository.ModeRepository;
import com.callblockerpro.app.domain.repository.ScheduleRepository;
import com.callblockerpro.app.domain.scheduler.SchedulerManager;
import com.callblockerpro.app.scheduler.SchedulerWorker;
import com.callblockerpro.app.scheduler.SchedulerWorker_AssistedFactory;
import com.callblockerpro.app.service.AutoBackupWorker;
import com.callblockerpro.app.service.AutoBackupWorker_AssistedFactory;
import com.callblockerpro.app.service.CallBlockingService;
import com.callblockerpro.app.service.CallBlockingService_MembersInjector;
import com.callblockerpro.app.ui.viewmodel.AddViewModel;
import com.callblockerpro.app.ui.viewmodel.AddViewModel_HiltModules_KeyModule_ProvideFactory;
import com.callblockerpro.app.ui.viewmodel.DashboardViewModel;
import com.callblockerpro.app.ui.viewmodel.DashboardViewModel_HiltModules_KeyModule_ProvideFactory;
import com.callblockerpro.app.ui.viewmodel.ListsViewModel;
import com.callblockerpro.app.ui.viewmodel.ListsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.callblockerpro.app.ui.viewmodel.OnboardingViewModel;
import com.callblockerpro.app.ui.viewmodel.OnboardingViewModel_HiltModules_KeyModule_ProvideFactory;
import com.callblockerpro.app.ui.viewmodel.SplashViewModel;
import com.callblockerpro.app.ui.viewmodel.SplashViewModel_HiltModules_KeyModule_ProvideFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SetBuilder;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerCallBlockerApplication_HiltComponents_SingletonC {
  private DaggerCallBlockerApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public CallBlockerApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements CallBlockerApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public CallBlockerApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements CallBlockerApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public CallBlockerApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements CallBlockerApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public CallBlockerApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements CallBlockerApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CallBlockerApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements CallBlockerApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CallBlockerApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements CallBlockerApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public CallBlockerApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements CallBlockerApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public CallBlockerApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends CallBlockerApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends CallBlockerApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends CallBlockerApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends CallBlockerApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(5).add(AddViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(DashboardViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ListsViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(OnboardingViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(SplashViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectUserPreferencesRepository(instance, singletonCImpl.userPreferencesRepositoryProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends CallBlockerApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddViewModel> addViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<ListsViewModel> listsViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.listsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(5).put("com.callblockerpro.app.ui.viewmodel.AddViewModel", ((Provider) addViewModelProvider)).put("com.callblockerpro.app.ui.viewmodel.DashboardViewModel", ((Provider) dashboardViewModelProvider)).put("com.callblockerpro.app.ui.viewmodel.ListsViewModel", ((Provider) listsViewModelProvider)).put("com.callblockerpro.app.ui.viewmodel.OnboardingViewModel", ((Provider) onboardingViewModelProvider)).put("com.callblockerpro.app.ui.viewmodel.SplashViewModel", ((Provider) splashViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return Collections.<String, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.callblockerpro.app.ui.viewmodel.AddViewModel 
          return (T) new AddViewModel(singletonCImpl.listRepositoryImplProvider.get());

          case 1: // com.callblockerpro.app.ui.viewmodel.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.bindModeRepositoryProvider.get(), singletonCImpl.bindCallLogRepositoryProvider.get());

          case 2: // com.callblockerpro.app.ui.viewmodel.ListsViewModel 
          return (T) new ListsViewModel(singletonCImpl.listRepositoryImplProvider.get());

          case 3: // com.callblockerpro.app.ui.viewmodel.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.userPreferencesRepositoryProvider.get());

          case 4: // com.callblockerpro.app.ui.viewmodel.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.userPreferencesRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends CallBlockerApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends CallBlockerApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectCallBlockingService(CallBlockingService callBlockingService) {
      injectCallBlockingService2(callBlockingService);
    }

    private CallBlockingService injectCallBlockingService2(CallBlockingService instance) {
      CallBlockingService_MembersInjector.injectDecisionEngine(instance, singletonCImpl.provideCallDecisionEngineProvider.get());
      CallBlockingService_MembersInjector.injectListRepository(instance, singletonCImpl.listRepositoryImplProvider.get());
      CallBlockingService_MembersInjector.injectModeRepository(instance, singletonCImpl.bindModeRepositoryProvider.get());
      CallBlockingService_MembersInjector.injectEmergencyNumberValidator(instance, singletonCImpl.emergencyNumberValidatorProvider.get());
      CallBlockingService_MembersInjector.injectCallLogRepository(instance, singletonCImpl.bindCallLogRepositoryProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends CallBlockerApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AutoBackupWorker_AssistedFactory> autoBackupWorker_AssistedFactoryProvider;

    private Provider<AppDatabase> provideAppDatabaseProvider;

    private Provider<ScheduleRepositoryImpl> scheduleRepositoryImplProvider;

    private Provider<ScheduleRepository> bindScheduleRepositoryProvider;

    private Provider<PreferenceManagerImpl> preferenceManagerImplProvider;

    private Provider<ModeRepositoryImpl> modeRepositoryImplProvider;

    private Provider<ModeRepository> bindModeRepositoryProvider;

    private Provider<SchedulerManager> provideSchedulerManagerProvider;

    private Provider<SchedulerWorker_AssistedFactory> schedulerWorker_AssistedFactoryProvider;

    private Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

    private Provider<ListRepositoryImpl> listRepositoryImplProvider;

    private Provider<CallLogRepositoryImpl> callLogRepositoryImplProvider;

    private Provider<CallLogRepository> bindCallLogRepositoryProvider;

    private Provider<CallDecisionEngine> provideCallDecisionEngineProvider;

    private Provider<PrivacyRemoteConfigRepository> privacyRemoteConfigRepositoryProvider;

    private Provider<EmergencyNumberValidator> emergencyNumberValidatorProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private ScheduleDao scheduleDao() {
      return DatabaseModule_ProvideScheduleDaoFactory.provideScheduleDao(provideAppDatabaseProvider.get());
    }

    private ModeHistoryDao modeHistoryDao() {
      return DatabaseModule_ProvideModeHistoryDaoFactory.provideModeHistoryDao(provideAppDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return MapBuilder.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>newMapBuilder(2).put("com.callblockerpro.app.service.AutoBackupWorker", ((Provider) autoBackupWorker_AssistedFactoryProvider)).put("com.callblockerpro.app.scheduler.SchedulerWorker", ((Provider) schedulerWorker_AssistedFactoryProvider)).build();
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private WhitelistDao whitelistDao() {
      return DatabaseModule_ProvideWhitelistDaoFactory.provideWhitelistDao(provideAppDatabaseProvider.get());
    }

    private BlocklistDao blocklistDao() {
      return DatabaseModule_ProvideBlocklistDaoFactory.provideBlocklistDao(provideAppDatabaseProvider.get());
    }

    private CallLogDao callLogDao() {
      return DatabaseModule_ProvideCallLogDaoFactory.provideCallLogDao(provideAppDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.autoBackupWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<AutoBackupWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 3));
      this.scheduleRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 2);
      this.bindScheduleRepositoryProvider = DoubleCheck.provider((Provider) scheduleRepositoryImplProvider);
      this.preferenceManagerImplProvider = DoubleCheck.provider(new SwitchingProvider<PreferenceManagerImpl>(singletonCImpl, 5));
      this.modeRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 4);
      this.bindModeRepositoryProvider = DoubleCheck.provider((Provider) modeRepositoryImplProvider);
      this.provideSchedulerManagerProvider = DoubleCheck.provider(new SwitchingProvider<SchedulerManager>(singletonCImpl, 6));
      this.schedulerWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<SchedulerWorker_AssistedFactory>(singletonCImpl, 1));
      this.userPreferencesRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserPreferencesRepository>(singletonCImpl, 7));
      this.listRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ListRepositoryImpl>(singletonCImpl, 8));
      this.callLogRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 9);
      this.bindCallLogRepositoryProvider = DoubleCheck.provider((Provider) callLogRepositoryImplProvider);
      this.provideCallDecisionEngineProvider = DoubleCheck.provider(new SwitchingProvider<CallDecisionEngine>(singletonCImpl, 10));
      this.privacyRemoteConfigRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PrivacyRemoteConfigRepository>(singletonCImpl, 12));
      this.emergencyNumberValidatorProvider = DoubleCheck.provider(new SwitchingProvider<EmergencyNumberValidator>(singletonCImpl, 11));
    }

    @Override
    public void injectCallBlockerApplication(CallBlockerApplication callBlockerApplication) {
      injectCallBlockerApplication2(callBlockerApplication);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private CallBlockerApplication injectCallBlockerApplication2(CallBlockerApplication instance) {
      CallBlockerApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.callblockerpro.app.service.AutoBackupWorker_AssistedFactory 
          return (T) new AutoBackupWorker_AssistedFactory() {
            @Override
            public AutoBackupWorker create(Context appContext, WorkerParameters workerParams) {
              return new AutoBackupWorker(appContext, workerParams);
            }
          };

          case 1: // com.callblockerpro.app.scheduler.SchedulerWorker_AssistedFactory 
          return (T) new SchedulerWorker_AssistedFactory() {
            @Override
            public SchedulerWorker create(Context context, WorkerParameters params) {
              return new SchedulerWorker(context, params, singletonCImpl.bindScheduleRepositoryProvider.get(), singletonCImpl.bindModeRepositoryProvider.get(), singletonCImpl.preferenceManagerImplProvider.get(), singletonCImpl.provideSchedulerManagerProvider.get());
            }
          };

          case 2: // com.callblockerpro.app.data.repository.ScheduleRepositoryImpl 
          return (T) new ScheduleRepositoryImpl(singletonCImpl.scheduleDao());

          case 3: // com.callblockerpro.app.data.local.AppDatabase 
          return (T) DatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.callblockerpro.app.data.repository.ModeRepositoryImpl 
          return (T) new ModeRepositoryImpl(singletonCImpl.preferenceManagerImplProvider.get(), singletonCImpl.modeHistoryDao());

          case 5: // com.callblockerpro.app.data.local.PreferenceManagerImpl 
          return (T) new PreferenceManagerImpl(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 6: // com.callblockerpro.app.domain.scheduler.SchedulerManager 
          return (T) SchedulerModule_ProvideSchedulerManagerFactory.provideSchedulerManager();

          case 7: // com.callblockerpro.app.data.repository.UserPreferencesRepository 
          return (T) new UserPreferencesRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 8: // com.callblockerpro.app.data.repository.ListRepositoryImpl 
          return (T) new ListRepositoryImpl(singletonCImpl.whitelistDao(), singletonCImpl.blocklistDao());

          case 9: // com.callblockerpro.app.data.repository.CallLogRepositoryImpl 
          return (T) new CallLogRepositoryImpl(singletonCImpl.callLogDao());

          case 10: // com.callblockerpro.app.domain.logic.CallDecisionEngine 
          return (T) DomainModule_ProvideCallDecisionEngineFactory.provideCallDecisionEngine();

          case 11: // com.callblockerpro.app.domain.logic.EmergencyNumberValidator 
          return (T) new EmergencyNumberValidator(singletonCImpl.privacyRemoteConfigRepositoryProvider.get());

          case 12: // com.callblockerpro.app.data.repository.PrivacyRemoteConfigRepository 
          return (T) new PrivacyRemoteConfigRepository();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
