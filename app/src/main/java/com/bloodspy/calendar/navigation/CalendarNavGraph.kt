package com.bloodspy.calendar.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bloodspy.calendar.navigation.Screen.LocationPicker.ARG_INITIAL_LOCATION
import com.bloodspy.calendar.presentation.addEditEvent.AddEditEventScreen
import com.bloodspy.calendar.presentation.calendar.CalendarScreen
import com.bloodspy.calendar.presentation.location.LocationPickerScreen

@Composable
fun CalendarNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    startDestination: String = Screen.Calendar.route,
    navActions: CalendarNavigationActions = remember(navController, keyboardController) {
        CalendarNavigationActions(navController, keyboardController)
    },
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Calendar.route) {
            CalendarScreen(
                modifier = Modifier.fillMaxSize(),
                onMenuClick = {},
                onTasksClick = {},
                onAddTaskClick = { navActions.navigateToAddEditEvent() },
                onCalendarItemClick = {}
            )
        }

        composable(route = Screen.AddEditEvent.route) {
            AddEditEventScreen(
                modifier = Modifier.fillMaxSize(),
                onArrowBackClick = { navActions.navigateUp() },
                onSaveButtonClick = {},
                onLocationClick = { location -> navActions.navigateToLocationPicker(location) }
            )
        }

        composable(route = Screen.LocationPicker.route) {
            LocationPickerScreen(
                modifier = Modifier.fillMaxSize(),
                onArrowBackClick = { navActions.navigateUp() }
            )
        }
    }
}