package com.example.domain.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun Long.toDomainDateTime() =
    Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())

fun dateTimeToEpoch(date: LocalDate, time: LocalTime): Long {
    // Пример для kotlinx-datetime
    return LocalDateTime(date, time)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}