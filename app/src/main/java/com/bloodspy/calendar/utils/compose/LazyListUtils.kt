package com.bloodspy.calendar.utils.compose

import androidx.compose.foundation.lazy.LazyListState

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