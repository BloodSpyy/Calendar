package com.bloodspy.calendar.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.unit.dp

val ICON_BUTTON_SIZE = 24.dp

fun isClickedMonthFullyVisible(lazyListState: LazyListState, clickedMonthIndex: Int): Boolean {
    with(lazyListState.layoutInfo) {
        val clickedMonthIndexVisibleInfo = visibleItemsInfo.find { it.index == clickedMonthIndex }

        return if (clickedMonthIndexVisibleInfo == null) {
            false
        } else {
            val isBeyondRightBorder =
                viewportEndOffset - clickedMonthIndexVisibleInfo.offset < clickedMonthIndexVisibleInfo.size
            val isBeyondLeftBorder =
                viewportStartOffset + clickedMonthIndexVisibleInfo.offset < clickedMonthIndexVisibleInfo.size

            return !isBeyondRightBorder && !isBeyondLeftBorder
        }
    }
}