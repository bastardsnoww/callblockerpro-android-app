package com.callblockerpro.app.ui.screens

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatLogTime(timestamp: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
        .withZone(ZoneId.systemDefault())
    return formatter.format(timestamp)
}
