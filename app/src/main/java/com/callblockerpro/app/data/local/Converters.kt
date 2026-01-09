package com.callblockerpro.app.data.local

import androidx.room.TypeConverter
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.CallResult
import com.callblockerpro.app.domain.model.ChangeSource
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalTime
import java.util.stream.Collectors

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? {
        return date?.toEpochMilli()
    }

    @TypeConverter
    fun fromLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }

    @TypeConverter
    fun localTimeToString(time: LocalTime?): String? {
        return time?.toString()
    }

    @TypeConverter
    fun fromAppMode(value: String?): AppMode? {
        return value?.let { AppMode.valueOf(it) }
    }

    @TypeConverter
    fun appModeToString(mode: AppMode?): String? {
        return mode?.name
    }

    @TypeConverter
    fun fromCallResult(value: String?): CallResult? {
        return value?.let { CallResult.valueOf(it) }
    }

    @TypeConverter
    fun callResultToString(result: CallResult?): String? {
        return result?.name
    }

    @TypeConverter
    fun fromChangeSource(value: String?): ChangeSource? {
        return value?.let { ChangeSource.valueOf(it) }
    }

    @TypeConverter
    fun changeSourceToString(source: ChangeSource?): String? {
        return source?.name
    }

    @TypeConverter
    fun fromDayOfWeekSet(value: String?): Set<DayOfWeek>? {
        if (value.isNullOrEmpty()) return emptySet()
        return value.split(",").map { DayOfWeek.valueOf(it) }.toSet()
    }

    @TypeConverter
    fun dayOfWeekSetToString(set: Set<DayOfWeek>?): String? {
        return set?.joinToString(",") { it.name }
    }
}
