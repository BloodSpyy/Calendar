package com.bloodspy.calendar.presentation.location

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodspy.calendar.R
import com.bloodspy.calendar.domain.CalendarRepository
import com.bloodspy.calendar.domain.product.AddressProduct
import com.bloodspy.calendar.navigation.Screen
import com.bloodspy.calendar.utils.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class LocationPickerUiState(
    val location: String,
    val addressesSuggestion: List<AddressProduct> = listOf(),
    val userMessage: Int? = null
)

@HiltViewModel
class LocationPickerViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

    private val _location: MutableStateFlow<String> = MutableStateFlow(
        savedStateHandle[Screen.LocationPicker.ARG_INITIAL_LOCATION] ?: ""
    )

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val _addressSuggestion: Flow<Async<List<AddressProduct>>> = _location
        .debounce(250)
        .distinctUntilChanged()
        .mapLatest { location ->
            Async.Success(calendarRepository.getAddressesSuggestion(location))
        }
        .onStart<Async<List<AddressProduct>>> { emit(Async.Loading) }
        .catch {
            Log.d(LOG_TAG, it.stackTraceToString())
            emit(Async.Error(errorMessage = R.string.location_picker_loading_locations_error))
        }

    val uiState: StateFlow<LocationPickerUiState> = combine(
        _location, _addressSuggestion, _userMessage
    ) { location, suggestions, message ->
        when (suggestions) {
            Async.Loading -> LocationPickerUiState(location = location)

            is Async.Error -> LocationPickerUiState(
                location = location,
                userMessage = suggestions.errorMessage
            )

            is Async.Success -> LocationPickerUiState(
                location = location,
                addressesSuggestion = suggestions.data,
                userMessage = message
            )
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = LocationPickerUiState(location = _location.value)
        )

    fun onLocationChanged(newLocation: String) {
        _location.value = newLocation
    }

    fun onSnackbarMessageShown() {
        _userMessage.value = null
    }

    companion object {
        private const val LOG_TAG = "LocationPickerViewModel"
    }
}