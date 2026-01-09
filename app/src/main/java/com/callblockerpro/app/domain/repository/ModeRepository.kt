package com.callblockerpro.app.domain.repository

import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ModeHistory
import com.callblockerpro.app.domain.model.ChangeSource
import kotlinx.coroutines.flow.Flow

interface ModeRepository {
    val currentMode: Flow<AppMode>
    fun getModeHistory(): Flow<List<ModeHistory>>
    
    suspend fun setMode(mode: AppMode, source: ChangeSource, scheduleId: Long? = null)
    fun getCurrentMode(): AppMode
}
