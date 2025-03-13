package com.bloodspy.calendar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bloodspy.calendar.navigation.CalendarScreens.CALENDAR_SCREEN

object CalendarScreens {
    const val CALENDAR_SCREEN = "calendar"
}

object CalendarDestinations {
    const val CALENDAR_ROUTE = CALENDAR_SCREEN
}

class CalendarNavigationActions(private val navController: NavController) {}