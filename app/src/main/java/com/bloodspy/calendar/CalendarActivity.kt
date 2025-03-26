package com.bloodspy.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bloodspy.calendar.navigation.CalendarNavGraph
import com.bloodspy.calendar.theme.CalendarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalendarTheme {
                CalendarNavGraph()
            }
        }
    }
}
