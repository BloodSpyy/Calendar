package com.bloodspy.calendar.utils

/**
 * A generic class that holds a loading signal or the result of an async operation.
 */
sealed class Async<out T> {
    data class Error(val errorMessage: Int) : Async<Nothing>()

    data class Success<out T>(val data: T) : Async<T>()

    data object Loading : Async<Nothing>()
}