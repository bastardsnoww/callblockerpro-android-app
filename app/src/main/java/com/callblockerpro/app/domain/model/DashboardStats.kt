package com.callblockerpro.app.domain.model

@androidx.compose.runtime.Immutable
data class DashboardStats(
    val callsScreened: Int,
    val spamBlocked: Int,
    val whitelisted: Int,
    val weeklyActivity: List<Float> = emptyList()
)
