package com.bloodspy.calendar.presentation.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bloodspy.calendar.R
import com.bloodspy.calendar.domain.CalendarProduct
import com.bloodspy.calendar.utils.BoxCentered
import com.bloodspy.calendar.utils.CalendarTopAppBar
import com.bloodspy.calendar.utils.DAYS_IN_WEEK
import com.bloodspy.calendar.utils.LoadingProgress
import java.time.LocalDate

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CalendarTopAppBar(
                // Index adjustment: months start at 1, array at 0
                title = stringArrayResource(R.array.months_of_year)[uiState.value.month - 1],
                onMenuClick = { viewModel.onLeftSwipe() },
                onTasksClick = { viewModel.onRightSwipe() }
            )
        }
    ) { paddingValues ->
        CalendarContent(
            modifier = Modifier.padding(paddingValues),
            calendarsProduct = uiState.value.items,
            isLoading = uiState.value.isLoading
        )
    }
}

@Composable
private fun CalendarContent(
    modifier: Modifier = Modifier,
    calendarsProduct: List<CalendarProduct>,
    isLoading: Boolean,
) {
    val daysOfWeek = stringArrayResource(R.array.days_of_week)

    if (isLoading) {
        BoxCentered(modifier = Modifier.fillMaxSize()) { LoadingProgress() }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(DAYS_IN_WEEK),
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(daysOfWeek) { dayOfWeek ->
                Text(
                    text = dayOfWeek,
                    textAlign = TextAlign.Center
                )
            }

            items(calendarsProduct) { calendarProduct ->
                val isCurrentDate = calendarProduct.date == LocalDate.now()

                CalendarItem(
                    calendarItem = calendarProduct,
                    isCurrentDate = isCurrentDate
                )
            }
        }
    }
}

@Composable
private fun CalendarItem(
    modifier: Modifier = Modifier,
    calendarItem: CalendarProduct,
    isCurrentDate: Boolean,
) {
    val columnModifier = modifier
        .fillMaxSize()
        .then(
            if (isCurrentDate) {
                Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onBackground)
            } else {
                Modifier
            }
        )
        .padding(12.dp)

    val textColor = if (isCurrentDate) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    Column(modifier = columnModifier) {
        Text(
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            color = textColor,
            text = calendarItem.date.dayOfMonth.toString()
        )
    }
}
