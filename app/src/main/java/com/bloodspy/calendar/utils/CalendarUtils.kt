package com.bloodspy.calendar.utils

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.YearMonth
import java.time.ZoneId

const val DAYS_IN_WEEK = 7
const val DAYS_ON_SCREEN = 42
const val MONTHS_IN_YEAR = 12
const val FIRST_DAY = 1

/** Get the days of the month to create a calendar */
fun getDaysOfMonth(
    firstDayOfMonth: LocalDate,
    startDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
): List<LocalDate> {
    val daysOfMonth = mutableListOf<LocalDate>()

    val firstDayOfNextMonth = firstDayOfMonth.plusMonths(1)
    val lengthOfMonth = firstDayOfMonth.lengthOfMonth()

    // Calculate the offset for the first day of the month relative to the start day of the week.
    // This determines how many empty slots are needed before the first day in a calendar grid.
    val startOffset = if (firstDayOfMonth.dayOfWeek.value > startDayOfWeek.value) {
        firstDayOfMonth.dayOfWeek.value - startDayOfWeek.value
    } else {
        firstDayOfMonth.dayOfWeek.value + DAYS_IN_WEEK - startDayOfWeek.value
    }

    // add days before month
    for (i in startOffset downTo 1) {
        daysOfMonth.add(
            firstDayOfMonth.minusDays(i.toLong())
        )
    }

    // add month days
    for (i in 0..<lengthOfMonth) {
        daysOfMonth.add(
            firstDayOfMonth.plusDays(i.toLong())
        )
    }

    // add days after month
    for (i in 0..<DAYS_ON_SCREEN - daysOfMonth.size) {
        daysOfMonth.add(
            firstDayOfNextMonth.plusDays(i.toLong())
        )
    }

    return daysOfMonth
}

fun Int.getMonthsCarousel(yearsToShow: Int = 100): List<YearMonth> {
    val monthsCarousel = mutableListOf<YearMonth>()

    for (year in this - yearsToShow..this + yearsToShow) {
        for (month in 1..MONTHS_IN_YEAR) {
            monthsCarousel.add(YearMonth.of(year, month))
        }
    }

    return monthsCarousel
}

fun LocalDateTime.toTimestamp(): Long = this.atZone(ZoneId.systemDefault()).toEpochSecond()

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochSecond(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDate.toTimestamp(): Long =
    this.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond