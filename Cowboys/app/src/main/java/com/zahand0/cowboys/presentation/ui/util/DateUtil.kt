package com.zahand0.cowboys.presentation.ui.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun toDateString(rawIso: String, pattern: String): String {
    val ta = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(rawIso)
    val i = Instant.from(ta)
    val formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
    return formatter.format(i)
}