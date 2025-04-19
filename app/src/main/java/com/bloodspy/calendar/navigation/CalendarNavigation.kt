package com.bloodspy.calendar.navigation

import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavController

sealed class Screen(val route: String) {
    data object Calendar : Screen("calendar")

    data object AddEditEvent : Screen("addEditEvent")

    data object LocationPicker : Screen("location/{initialLocation}") {
        const val ARG_INITIAL_LOCATION = "initialLocation"

        fun createRoute(initialLocation: String): String =
            "location/$initialLocation"
    }
}

class CalendarNavigationActions(
    private val navController: NavController,
    private val keyboardController: SoftwareKeyboardController?
) {
    fun navigateUp() {
        keyboardController?.hide()
        navController.popBackStack()
    }

    fun navigateToAddEditEvent() {
        navController.navigate(Screen.AddEditEvent.route) {
            launchSingleTop = true
        }
    }

    fun navigateToLocationPicker(initialLocation: String) {
        navController.navigate(Screen.LocationPicker.createRoute(initialLocation)) {
            launchSingleTop = true
        }
    }
}