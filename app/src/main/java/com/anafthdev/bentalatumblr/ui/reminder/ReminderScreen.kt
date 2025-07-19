package com.anafthdev.bentalatumblr.ui.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme

@Composable
@Preview(showSystemUi = true)
private fun ReminderScreenPreview() {
    BentalaTumblrTheme {
        ReminderScreenContent(
            state = ReminderState(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun ReminderScreen(
    viewModel: ReminderViewModel,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {}
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    var showAddRecordBottomSheet by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .systemBarsPadding()
    ) {
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null
            )
        }

        ReminderScreenContent(
            state = state,
            onNavigationIconClick = onNavigationIconClick,
            modifier = modifier
                .fillMaxSize()
                .systemBarsPadding()
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderScreenContent(
    state: ReminderState,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {}
) {

//    LazyColumn(
//        contentPadding = PaddingValues(horizontal = 16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = modifier
//    ) {
//        item {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(stringResource(R.string.reminder_reminder))
//                },
//                navigationIcon = {
//                    IconButton(
//                        onClick = onNavigationIconClick
//                    ) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
//                            contentDescription = null
//                        )
//                    }
//                }
//            )
//        }
//
//        if (state.reminders.isEmpty()) {
//            item {
//                Text(
//                    text = "No reminders",
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                )
//            }
//        }
//
//        item {
//            Spacer(Modifier.height(FloatingActionButtonDefaults.LargeIconSize + 16.dp))
//        }
//    }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        item {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.reminder_reminder))
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigationIconClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }

        if (state.reminders.isEmpty()) {
            item {
                Text(
                    text = "No reminders",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        item {
            Spacer(Modifier.height(FloatingActionButtonDefaults.LargeIconSize + 16.dp))
        }
    }

}
