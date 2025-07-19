package com.anafthdev.bentalatumblr.ui.find_tumblr

import android.icu.text.SimpleDateFormat
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import java.util.Locale

private class BooleanPreviewParameter: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Composable
@Preview
private fun BottleStatusPreview(
    @PreviewParameter(BooleanPreviewParameter::class) expanded: Boolean
) {
    BentalaTumblrTheme {
        BottleStatusCard(
            state = FindTumblrState(),
            expanded = expanded,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview(showSystemUi = true)
private fun FindTumblrScreenPreview() {
    BentalaTumblrTheme {
        FindTumblrScreenContent(
            state = FindTumblrState(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun FindTumblrScreen(
    viewModel: FindTumblrViewModel,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FindTumblrScreenContent(
        state = state,
        onNavigationIconClick = onNavigationIconClick,
        modifier = modifier
            .fillMaxSize()
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FindTumblrScreenContent(
    state: FindTumblrState,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {}
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text("Find My Bentala")
            },
            navigationIcon = {
                IconButton(
                    onClick = onNavigationIconClick
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Image(
                painter = painterResource(R.drawable.map_placeholder),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp)
            ) {
                FloatingActionButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MyLocation,
                        contentDescription = null
                    )
                }

                BottleStatusCard(
                    state = state,
                    expanded = expanded,
                    onClick = {
                        expanded = !expanded
                    }
                )
            }
        }

    }

}

@Composable
private fun BottleStatusCard(
    state: FindTumblrState,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    OutlinedCard(
        onClick = onClick,
        modifier = modifier
            .animateContentSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Tumblr Status",
                style = MaterialTheme.typography.titleLarge
            )

            AnimatedContent(
                targetState = expanded,
            ) { s ->
                if (s) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        BottleStatusItem(
                            iconResId = R.drawable.ic_bottle,
                            text = "Tumblr Name: ${state.tumblrName}"
                        )

                        BottleStatusItem(
                            iconResId = R.drawable.ic_clock,
                            text = "Last Updated: ${SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault()).format(state.lastUpdate)}"
                        )

                        BottleStatusItem(
                            iconResId = R.drawable.ic_location,
                            text = "Location: ${state.locationText}"
                        )

                        BottleStatusItem(
                            iconResId = R.drawable.ic_location_coordinate,
                            text = "Coordinate: ${state.locationCoordinate.first}, ${state.locationCoordinate.second}"
                        )

                        BottleStatusItem(
                            iconResId = R.drawable.ic_temperature,
                            text = "Temperature: ${state.tumblrTemperature}°C"
                        )

                        BottleStatusItem(
                            iconResId = R.drawable.ic_battery,
                            text = "Battery: ${state.tumblrBattery}%"
                        )
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "Last Updated: ${SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis())}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Location: Jl raya Susukan, Bojonggede, Bogor, Jawa Barat",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottleStatusItem(
    iconResId: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
