package com.callblockerpro.app.data.remote

import android.content.Context
import android.content.Intent
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class GoogleAuthManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getSignInIntent(): Intent? = null
    
    suspend fun handleSignInResult(intent: Intent?): Result<Boolean> {
         return Result.failure(Exception("Backup removed for light version"))
    }
    
    fun isSignedIn(): Boolean = false
    
    fun signOut() {}
}
