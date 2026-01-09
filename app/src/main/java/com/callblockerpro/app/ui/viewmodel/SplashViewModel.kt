package com.callblockerpro.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.callblockerpro.app.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val isOnboardingCompleted: Flow<Boolean> = userPreferencesRepository.isOnboardingCompleted
}
