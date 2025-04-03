package com.bloodspy.calendar.navigation

import androidx.navigation.NavController
import com.bloodspy.calendar.navigation.CalendarDestinations.ADD_TASK_ROUTE
import com.bloodspy.calendar.navigation.CalendarScreens.ADD_TASK_SCREEN
import com.bloodspy.calendar.navigation.CalendarScreens.CALENDAR_SCREEN

object CalendarScreens {
    const val CALENDAR_SCREEN = "calendar"
    const val ADD_TASK_SCREEN = "addTask"
}

object CalendarDestinations {
    const val CALENDAR_ROUTE = CALENDAR_SCREEN
    const val ADD_TASK_ROUTE = ADD_TASK_SCREEN
}

class CalendarNavigationActions(private val navController: NavController) {
    fun navigateToAddTask() {
        navController.navigate(ADD_TASK_ROUTE)
    }
}