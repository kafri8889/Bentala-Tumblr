package com.anafthdev.bentalatumblr.ui.setting

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.data.Destinations
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import kotlinx.parcelize.Parcelize

@Composable
@Preview(showSystemUi = true)
private fun SettingScreenPreview() {
    BentalaTumblrTheme {
        SettingScreenContent(
            state = SettingState(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    modifier: Modifier = Modifier,
    onNavigateTo: (Destinations) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingScreenContent(
        state = state,
        onSettingItemClick = { item ->
            when (item) {
                SettingItem.reminder -> onNavigateTo(Destinations.Reminder)
            }
        },
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    )

}

@Composable
private fun SettingScreenContent(
    state: SettingState,
    modifier: Modifier = Modifier,
    onSettingItemClick: (SettingItem) -> Unit = {}
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(
            items = SettingItem.items
        ) { item ->
            ListItem(
                headlineContent = {
                    Text(text = stringResource(id = item.title))
                },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .clickable {
                        onSettingItemClick(item)
                    }
            )
        }
    }

}

@Immutable
@Parcelize
data class SettingItem(
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int,
): Parcelable {

    companion object {
        val reminder = SettingItem(
            title = R.string.setting_item_reminder,
            icon = R.drawable.ic_bell
        )

        val items = listOf(
            reminder
        )
    }

}
