package com.anafthdev.bentalatumblr.ui.marketplace

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.foundation.component.AccessoryItem
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme

@Composable
@Preview(showSystemUi = true)
private fun MarketplaceScreenPreview() {
    BentalaTumblrTheme {
        MarketplaceScreenContent(
            state = MarketplaceState(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(
    viewModel: MarketplaceViewModel,
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.marketplace_bentala_rewards))
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
        }
    ) { scaffoldPadding ->
        MarketplaceScreenContent(
            state = state,
            modifier = modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        )
    }

}

@Composable
private fun MarketplaceScreenContent(
    state: MarketplaceState,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            PointCard(
                point = state.point,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        items(
            items = state.items,
            key = { it.id },
        ) { item ->
            AccessoryItem(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PointCard(
    point: Int,
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color.White,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            )
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_bentala_logo_only),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                )

                Text(
                    text = stringResource(R.string.accessory_item_n_point, point),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color.White.copy(alpha = 0.2f),
                                Color.White.copy(alpha = 0.8f),
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.marketplace_get_point_summary),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .weight(1f)
                )

                Button(
                    onClick = onDetailClick
                ) {
                    Text("Detail")
                }
            }
        }
    }

}
