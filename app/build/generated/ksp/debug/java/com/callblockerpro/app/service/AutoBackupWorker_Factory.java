package com.callblockerpro.app.service;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AutoBackupWorker_Factory {
  public AutoBackupWorker_Factory() {
  }

  public AutoBackupWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams);
  }

  public static AutoBackupWorker_Factory create() {
    return new AutoBackupWorker_Factory();
  }

  public static AutoBackupWorker newInstance(Context appContext, WorkerParameters workerParams) {
    return new AutoBackupWorker(appContext, workerParams);
  }
}
