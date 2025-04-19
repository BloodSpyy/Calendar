package com.bloodspy.calendar.presentation.calendar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bloodspy.calendar.R
import com.bloodspy.calendar.constants.ICON_BUTTON_SIZE
import com.bloodspy.calendar.domain.product.CalendarProduct
import com.bloodspy.calendar.utils.DAYS_IN_WEEK
import com.bloodspy.calendar.utils.compose.CalendarTopAppBar
import com.bloodspy.calendar.utils.compose.isClickedMonthFullyVisible
import com.bloodspy.calendar.utils.getMonthsCarousel
import java.time.LocalDate
import java.time.YearMonth

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
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            CalendarTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                onMenuClick = onMenuClick,
                onTasksClick = onTasksClick,
                onHomeClick = viewModel::onHomeClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(R.string.calendar_screen_add_task_button_content_description)
                )
            }
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        CalendarContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            calendarsProduct = uiState.calendarItems,
            isMonthCarouselVisible = uiState.isMonthsCarouselVisible,
            monthWithYear = uiState.monthWithYear,
            onItemClick = onCalendarItemClick,
            onArrowBackClick = viewModel::onArrowBackClick,
            onArrowForwardClick = viewModel::onArrowForwardClick,
            onMonthWithYearClick = viewModel::onMonthWithYearClick,
            onMonthClick = viewModel::onMonthClick
        )
    }
}

@Composable
private fun CalendarContent(
    modifier: Modifier = Modifier,
    calendarsProduct: List<CalendarProduct>,
    isMonthCarouselVisible: Boolean,
    monthWithYear: YearMonth,
    onArrowBackClick: () -> Unit,
    onArrowForwardClick: () -> Unit,
    onMonthWithYearClick: () -> Unit,
    onItemClick: (CalendarProduct) -> Unit,
    onMonthClick: (YearMonth) -> Unit,
) {
    Column(modifier = modifier) {
        CalendarHeader(
            modifier = Modifier.fillMaxWidth(),
            monthWithYear = monthWithYear,
            onArrowBackClick = onArrowBackClick,
            onArrowForwardClick = onArrowForwardClick,
            onMonthWithYearClick = onMonthWithYearClick
        )

        AnimatedVisibility(
            isMonthCarouselVisible,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            MonthsCarousel(
                modifier = Modifier.fillMaxWidth(),
                monthWithYear = monthWithYear,
                onMonthClick = onMonthClick
            )
        }

        AnimatedContent(
            targetState = calendarsProduct,
            transitionSpec = {
                if (initialState.isEmpty() || targetState.isEmpty()) {
                    EnterTransition.None togetherWith ExitTransition.None
                } else if (targetState.first().date > initialState.first().date) {
                    slideInHorizontally { fullWidth ->
                        fullWidth
                    } + scaleIn() togetherWith slideOutHorizontally { fullWidth ->
                        -fullWidth
                    } + scaleOut()
                } else {
                    slideInHorizontally { fullWidth ->
                        -fullWidth
                    } + scaleIn() togetherWith slideOutHorizontally { fullWidth ->
                        fullWidth
                    } + scaleOut()
                }
            }
        ) { calendarsProduct ->
            CalendarGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                calendarsProduct = calendarsProduct,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun CalendarHeader(
    modifier: Modifier = Modifier,
    monthWithYear: YearMonth,
    onArrowBackClick: () -> Unit,
    onArrowForwardClick: () -> Unit,
    onMonthWithYearClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onArrowBackClick) {
            Icon(
                modifier = Modifier.size(ICON_BUTTON_SIZE),
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = stringResource(R.string.calendar_screen_top_app_bar_go_to_previous_month_content_description),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Row(modifier = Modifier.clickable { onMonthWithYearClick() }) {
            Text(
                text = String.format(
                    // Index adjustment: months start at 1, array at 0
                    stringArrayResource(R.array.calendar_screen_month_with_year)[monthWithYear.monthValue - 1],
                    monthWithYear.year
                ),
            )

            Icon(
                modifier = Modifier.size(ICON_BUTTON_SIZE),
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = stringResource(R.string.calendar_screen_top_app_bar_choose_month_content_description),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = onArrowForwardClick) {
            Icon(
                modifier = Modifier.size(ICON_BUTTON_SIZE),
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = stringResource(R.string.calendar_screen_top_app_bar_go_to_next_month_content_description),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun MonthsCarousel(
    modifier: Modifier = Modifier,
    monthWithYear: YearMonth,
    onMonthClick: (YearMonth) -> Unit,
) {
    val monthsCarousel = remember { monthWithYear.year.getMonthsCarousel() }
    val lazyListState =
        rememberLazyListState(
            initialFirstVisibleItemIndex = monthsCarousel.indexOf(monthWithYear)
        )

    LaunchedEffect(monthWithYear) {
        val monthIndex = monthsCarousel.indexOf(monthWithYear)

        if (!isClickedMonthFullyVisible(lazyListState, monthIndex)) {
            with(lazyListState.layoutInfo) {
                val itemScrollOffset = if (monthIndex >= visibleItemsInfo.last().index) {
                    viewportEndOffset - (visibleItemsInfo.last().size + visibleItemsInfo.first().size)
                } else {
                    visibleItemsInfo.first().size
                }

                lazyListState.animateScrollToItem(monthIndex, -itemScrollOffset)
            }
        }
    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        state = lazyListState
    ) {
        items(monthsCarousel, key = { it }) { monthToShow ->
            val (background, contentColor, borderColor) = if (monthToShow == monthWithYear) {
                Triple(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.primary
                )
            } else {
                Triple(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.onBackground,
                    MaterialTheme.colorScheme.secondaryContainer
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(percent = 15))
                    .background(background)
                    .border(width = 2.dp, color = borderColor)
                    .clickable { onMonthClick(monthToShow) }
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                text = String.format(
                    // Index adjustment: months start at 1, array at 0
                    stringArrayResource(R.array.calendar_screen_months_carousel)[monthToShow.monthValue - 1],
                    monthToShow.year
                ),
                color = contentColor
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    modifier: Modifier = Modifier,
    calendarsProduct: List<CalendarProduct>,
    onItemClick: (CalendarProduct) -> Unit,
) {
    val daysOfWeek = stringArrayResource(R.array.calendar_screen_days_of_week)

    LazyVerticalGrid(
        columns = GridCells.Fixed(DAYS_IN_WEEK),
        modifier = modifier
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(daysOfWeek, key = { it }) { dayOfWeek ->
            Text(
                text = dayOfWeek,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(calendarsProduct, key = { it.date }) { calendarProduct ->
            val isCurrentDate = calendarProduct.date == LocalDate.now()

            CalendarItem(
                modifier = Modifier.fillMaxSize(),
                calendarItem = calendarProduct,
                isCurrentDate = isCurrentDate,
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
    val (background, contentColor) = if (isCurrentDate) {
        Pair(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
    } else {
        Pair(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.onBackground)
    }

    val columnModifier = modifier
        .clip(CircleShape)
        .clickable { onItemClick(calendarItem) }
        .background(background)
        .padding(16.dp)

    Column(modifier = columnModifier) {
        Text(
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            color = contentColor,
            text = calendarItem.date.dayOfMonth.toString()
        )
    }
}