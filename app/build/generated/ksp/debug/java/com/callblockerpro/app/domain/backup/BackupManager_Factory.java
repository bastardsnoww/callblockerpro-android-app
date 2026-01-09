package com.callblockerpro.app.domain.backup;

import android.content.Context;
import com.callblockerpro.app.data.local.PreferenceManager;
import com.callblockerpro.app.data.remote.DriveServiceHelper;
import com.callblockerpro.app.domain.repository.BackupRepository;
import com.callblockerpro.app.domain.util.CryptographyManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class BackupManager_Factory implements Factory<BackupManager> {
  private final Provider<Context> contextProvider;

  private final Provider<BackupRepository> backupRepositoryProvider;

  private final Provider<PreferenceManager> preferenceManagerProvider;

  private final Provider<CryptographyManager> cryptographyManagerProvider;

  private final Provider<DriveServiceHelper> driveServiceHelperProvider;

  public BackupManager_Factory(Provider<Context> contextProvider,
      Provider<BackupRepository> backupRepositoryProvider,
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<CryptographyManager> cryptographyManagerProvider,
      Provider<DriveServiceHelper> driveServiceHelperProvider) {
    this.contextProvider = contextProvider;
    this.backupRepositoryProvider = backupRepositoryProvider;
    this.preferenceManagerProvider = preferenceManagerProvider;
    this.cryptographyManagerProvider = cryptographyManagerProvider;
    this.driveServiceHelperProvider = driveServiceHelperProvider;
  }

  @Override
  public BackupManager get() {
    return newInstance(contextProvider.get(), backupRepositoryProvider.get(), preferenceManagerProvider.get(), cryptographyManagerProvider.get(), driveServiceHelperProvider.get());
  }

  public static BackupManager_Factory create(Provider<Context> contextProvider,
      Provider<BackupRepository> backupRepositoryProvider,
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<CryptographyManager> cryptographyManagerProvider,
      Provider<DriveServiceHelper> driveServiceHelperProvider) {
    return new BackupManager_Factory(contextProvider, backupRepositoryProvider, preferenceManagerProvider, cryptographyManagerProvider, driveServiceHelperProvider);
  }

  public static BackupManager newInstance(Context context, BackupRepository backupRepository,
      PreferenceManager preferenceManager, CryptographyManager cryptographyManager,
      DriveServiceHelper driveServiceHelper) {
    return new BackupManager(context, backupRepository, preferenceManager, cryptographyManager, driveServiceHelper);
  }
}
