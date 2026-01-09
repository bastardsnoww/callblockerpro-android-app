package com.callblockerpro.app.data.repository

import com.callblockerpro.app.data.local.PreferenceManager
import com.callblockerpro.app.data.local.dao.ModeHistoryDao
import com.callblockerpro.app.data.local.entity.ModeHistoryEntity
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ChangeSource
import com.callblockerpro.app.domain.model.ModeHistory
import com.callblockerpro.app.domain.repository.ModeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant
import javax.inject.Inject

class ModeRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val modeHistoryDao: ModeHistoryDao
) : ModeRepository {

    // Volatile cache for O(1) synchronous access
    @Volatile
    private var cachedMode: AppMode? = null
    private val scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO)

    init {
        // Keep cache in sync with flow
        scope.launch {
            preferenceManager.currentMode.collect {
                cachedMode = it
            }
        }
    }

    override val currentMode: Flow<AppMode> = preferenceManager.currentMode

    override fun getModeHistory(): Flow<List<ModeHistory>> {
        return modeHistoryDao.getAll().map { list ->
            list.map { entity ->
                ModeHistory(
                    id = entity.id,
                    previousMode = entity.previousMode,
                    newMode = entity.newMode,
                    timestamp = entity.timestamp,
                    source = entity.source,
                    relatedScheduleId = entity.relatedScheduleId
                )
            }
        }
    }

    override suspend fun setMode(mode: AppMode, source: ChangeSource, scheduleId: Long?) {
        val current = cachedMode ?: try {
             preferenceManager.getMode()
        } catch (e: Exception) {
            AppMode.NEUTRAL
        }
        
        // If the user manually sets a mode, we update the "Last Manual Mode" so we can revert to it later
        if (source == ChangeSource.MANUAL) {
            preferenceManager.setLastManualMode(mode)
        }

        // Only record if it's actually a change (or maybe we log all sets? Let's log changes for now)
        if (current != mode) {
            preferenceManager.setMode(mode)
            // Cache update will be handled by the flow collector in init
            
            val historyEntry = ModeHistoryEntity(
                previousMode = current,
                newMode = mode,
                timestamp = Instant.now(),
                source = source,
                relatedScheduleId = scheduleId
            )
            modeHistoryDao.insert(historyEntry)
        }
    }

    override fun getCurrentMode(): AppMode {
        return cachedMode ?: kotlinx.coroutines.runBlocking {
            val mode = preferenceManager.getMode()
            cachedMode = mode
            mode
        }
    }
}
