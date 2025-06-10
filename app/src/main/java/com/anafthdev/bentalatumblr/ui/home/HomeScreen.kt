package com.anafthdev.bentalatumblr.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme

@Composable
@Preview(showSystemUi = true)
private fun HomeScreenPreview() {
    BentalaTumblrTheme {
        HomeScreenContent(
            state = HomeState(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = state,
        modifier = modifier
            .fillMaxSize()
    )

}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    modifier: Modifier = Modifier
) {

}
