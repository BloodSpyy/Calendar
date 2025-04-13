package com.bloodspy.calendar.utils.compose

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bloodspy.calendar.R
import com.bloodspy.calendar.constants.SPACE_BETWEEN_ICON_AND_FIELD

@Composable
fun CalendarDialog(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        ElevatedCard(
            modifier = modifier.background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.extraLarge
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontWeight = FontWeight.SemiBold,
                    text = stringResource(title)
                )

                content()
            }
        }
    }
}

@Composable
fun CalendarDialogWithButtons(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    onDismissRequest: () -> Unit,
    onAcceptButtonClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        ElevatedCard(
            modifier = modifier.background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.extraLarge
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontWeight = FontWeight.SemiBold,
                    text = stringResource(title)
                )

                content()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = onDismissRequest
                    ) {
                        Text(text = stringResource(R.string.anyone_screen_picker_cancel))
                    }

                    Button(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {
                            onAcceptButtonClick()
                            onDismissRequest()
                        }
                    ) {
                        Text(text = stringResource(R.string.anyone_screen_picker_ok))
                    }
                }
            }
        }
    }
}

@Composable
fun SingleSelectDialog(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    optionsList: List<String>,
    onDismissRequest: () -> Unit,
    selectionIndicator: @Composable (String) -> Unit,
    onItemSelected: (String) -> Unit
) {
    CalendarDialog(
        modifier = modifier,
        title = title,
        onDismissRequest = onDismissRequest,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(optionsList, key = { it }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemSelected(it) },
                    horizontalArrangement = Arrangement.spacedBy(SPACE_BETWEEN_ICON_AND_FIELD)
                ) {
                    selectionIndicator(it)

                    Text(
                        modifier = Modifier,
                        text = it
                    )
                }
            }
        }
    }
}