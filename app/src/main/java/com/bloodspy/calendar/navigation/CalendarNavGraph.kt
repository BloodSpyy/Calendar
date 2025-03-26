package com.bloodspy.calendar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bloodspy.calendar.presentation.calendar.CalendarScreen

@Composable
fun CalendarNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = CalendarDestinations.CALENDAR_ROUTE,
    navActions: CalendarNavigationActions = remember(navController) {
        CalendarNavigationActions(navController)
    },
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = CalendarDestinations.CALENDAR_ROUTE) {
            CalendarScreen(
                onMenuClick = {},
                onTasksClick = {},
                onAddTaskClick = {},
                onCalendarItemClick = {}
            )
        }
    }
}