package com.bloodspy.calendar.presentation.events

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bloodspy.calendar.R
import com.bloodspy.calendar.domain.CalendarProduct
import com.bloodspy.calendar.utils.BoxCentered
import com.bloodspy.calendar.utils.DAYS_IN_WEEK
import com.bloodspy.calendar.utils.LoadingProgress
import java.time.LocalDate

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
    onMenuClick: () -> Unit,
    onTasksClick: () -> Unit,
    onAddTaskClick: () -> Unit,
    onCalendarItemClick: (CalendarProduct) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CalendarTopAppBar(
                title = stringResource(R.string.app_name),
                onMenuClick = onMenuClick,
                onTasksClick = onTasksClick,
                onHomeClick = viewModel::onHomeClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.calendar_screen_add_task_button)
                )
            }
        }
    ) { paddingValues ->
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        CalendarContent(
            modifier = Modifier.padding(paddingValues),
            calendarsProduct = uiState.value.items,
            isLoading = uiState.value.isLoading,
            monthWithYear = uiState.value.monthWithYear,
            onItemClick = onCalendarItemClick,
            onArrowBackClick = viewModel::onArrowBackClick,
            onArrowForwardClick = viewModel::onArrowForwardClick
        )
    }
}

@Composable
private fun CalendarContent(
    modifier: Modifier = Modifier,
    calendarsProduct: List<CalendarProduct>,
    isLoading: Boolean,
    monthWithYear: Pair<Int, Int>,
    onArrowBackClick: () -> Unit,
    onArrowForwardClick: () -> Unit,
    onItemClick: (CalendarProduct) -> Unit,
) {
    if (isLoading) {
        BoxCentered(
            modifier = modifier
                .fillMaxSize()
                .zIndex(1f)
        ) { LoadingProgress() }
    }

    AnimatedContent(targetState = monthWithYear) {
        Column(modifier = modifier.fillMaxSize()) {
            CalendarHeader(
                monthWithYear = it,
                onArrowBackClick = onArrowBackClick,
                onArrowForwardClick = onArrowForwardClick,
                onMonthWithYearClick = {}
            )

            CalendarGrid(
                calendarsProduct = calendarsProduct,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun CalendarItem(
    modifier: Modifier = Modifier,
    calendarItem: CalendarProduct,
    isCurrentDate: Boolean,
    onItemClick: (CalendarProduct) -> Unit,
) {
    val backgroundColor = if (isCurrentDate) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.background
    }

    val textColor = if (isCurrentDate) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    val columnModifier = modifier
        .fillMaxSize()
        .clip(CircleShape)
        .clickable { onItemClick(calendarItem) }
        .background(backgroundColor)
        .padding(12.dp)

    Column(modifier = columnModifier) {
        Text(
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            color = textColor,
            text = calendarItem.date.dayOfMonth.toString()
        )
    }
}

@Composable
private fun CalendarHeader(
    monthWithYear: Pair<Int, Int>,
    onArrowBackClick: () -> Unit,
    onArrowForwardClick: () -> Unit,
    onMonthWithYearClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onArrowBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = stringResource(R.string.top_app_bar_go_to_previous_month)
            )
        }

        Row(modifier = Modifier.clickable { onMonthWithYearClick() }) {
            Text(
                text = String.format(
                    // Index adjustment: months start at 1, array at 0
                    stringArrayResource(R.array.months_with_year)[monthWithYear.first - 1],
                    monthWithYear.second
                ),
                fontSize = 18.sp
            )

            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = stringResource(R.string.top_app_bar_choose_month)
            )
        }

        IconButton(onClick = onArrowForwardClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = stringResource(R.string.top_app_bar_go_to_next_month)
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    calendarsProduct: List<CalendarProduct>,
    onItemClick: (CalendarProduct) -> Unit,
) {
    val daysOfWeek = stringArrayResource(R.array.days_of_week)

    LazyVerticalGrid(
        columns = GridCells.Fixed(DAYS_IN_WEEK),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(daysOfWeek, key = { it }) { dayOfWeek ->
            Text(
                text = dayOfWeek,
                textAlign = TextAlign.Center
            )
        }

        items(calendarsProduct, key = { it.date }) { calendarProduct ->
            val isCurrentDate = calendarProduct.date == LocalDate.now()

            CalendarItem(
                calendarItem = calendarProduct,
                isCurrentDate = isCurrentDate,
                onItemClick = onItemClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarTopAppBar(
    title: String,
    onTasksClick: () -> Unit,
    onMenuClick: () -> Unit,
    onHomeClick: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = stringResource(R.string.top_app_bar_open_menu)
                )
            }
        },
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onHomeClick) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = stringResource(R.string.top_app_bar_go_to_current_month)
                )
            }
            IconButton(onClick = onTasksClick) {
                Icon(
                    painter = painterResource(R.drawable.tasks),
                    contentDescription = stringResource(R.string.top_app_bar_open_settings)
                )
            }
        }
    )
}
