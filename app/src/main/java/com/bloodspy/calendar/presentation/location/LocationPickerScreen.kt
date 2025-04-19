package com.bloodspy.calendar.presentation.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bloodspy.calendar.R
import com.bloodspy.calendar.domain.product.AddressProduct
import com.bloodspy.calendar.utils.compose.AddEditInputField
import com.bloodspy.calendar.utils.compose.LocationPickerTopAppBar

@Composable
fun LocationPickerScreen(
    modifier: Modifier = Modifier,
    viewModel: LocationPickerViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            LocationPickerTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                onArrowBackClick = onArrowBackClick,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        LocationPickerContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            location = uiState.location,
            suggestions = uiState.addressesSuggestion,
            onLocationChanged = viewModel::onLocationChanged
        )
    }

    uiState.userMessage?.let { message ->
        val snackbarText = stringResource(message)
        LaunchedEffect(snackbarHostState, viewModel, message, snackbarText) {
            snackbarHostState.showSnackbar(snackbarText, duration = SnackbarDuration.Long)
            viewModel.onSnackbarMessageShown()
        }
    }
}

@Composable
fun LocationPickerContent(
    modifier: Modifier = Modifier,
    location: String,
    suggestions: List<AddressProduct>,
    onLocationChanged: (String) -> Unit
) {
    val screenPadding = 12.dp
    val dividerThickness = 1.dp

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier) {
        AddEditInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(screenPadding)
                .focusRequester(focusRequester),
            value = location,
            icon = R.drawable.location_city,
            contentDescription = R.string.location_picker_screen_location_content_description,
            hint = R.string.location_picker_screen_task_location_hint,
            onValueChanged = onLocationChanged,
        )

        HorizontalDivider(thickness = dividerThickness)

        LazyColumn {
            items(suggestions) {
                Text(text = it.addressSuggestion)
            }
        }
    }
}