package com.callblockerpro.app.domain.usecase

import com.callblockerpro.app.domain.model.CallLogEntry
import com.callblockerpro.app.domain.repository.CallLogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentCallsUseCase @Inject constructor(
    private val callLogRepository: CallLogRepository
) {
    operator fun invoke(limit: Int = 5): Flow<List<CallLogEntry>> {
        return callLogRepository.getRecentLogs(limit)
    }
}
