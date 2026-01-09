package com.callblockerpro.app.domain.usecase

import com.callblockerpro.app.domain.repository.CallLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

import com.callblockerpro.app.domain.model.DashboardStats

class GetDashboardStatsUseCase @Inject constructor(
    private val callLogRepository: CallLogRepository
) {
    operator fun invoke(): Flow<DashboardStats> = flow {
        // Optimized query using COUNT(*)
        emit(callLogRepository.getStats())
    }
}
