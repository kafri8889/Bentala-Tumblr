package com.anafthdev.bentalatumblr.ui.auth.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import kotlinx.coroutines.launch
import mx.platacard.pagerindicator.PagerIndicatorOrientation
import mx.platacard.pagerindicator.PagerWormIndicator

@Composable
@Preview(showSystemUi = true)
private fun OnboardingScreenPreview() {
    BentalaTumblrTheme {
        OnboardingScreenContent(
            state = OnboardingState(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    modifier: Modifier = Modifier,
    onGetStarted: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OnboardingScreenContent(
        state = state,
        onGetStarted = onGetStarted,
        modifier = modifier
            .fillMaxSize()
    )

}

@Composable
private fun OnboardingScreenContent(
    state: OnboardingState,
    modifier: Modifier = Modifier,
    onGetStarted: () -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState { 3 }

    val pagerModifier = Modifier
        .fillMaxWidth()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> Page(
                    painter = painterResource(R.drawable.onboarding_page_0_img),
                    title = stringResource(R.string.onboarding_page_0_title),
                    description = stringResource(R.string.onboarding_page_0_description),
                    modifier = pagerModifier
                )
                1 -> Page(
                    painter = painterResource(R.drawable.onboarding_page_1_img),
                    title = stringResource(R.string.onboarding_page_1_title),
                    description = stringResource(R.string.onboarding_page_1_description),
                    modifier = pagerModifier
                )
                2 -> Page(
                    painter = painterResource(R.drawable.onboarding_page_2_img),
                    title = stringResource(R.string.onboarding_page_2_title),
                    description = stringResource(R.string.onboarding_page_2_description),
                    modifier = pagerModifier
                )
            }
        }

        PagerWormIndicator(
            pagerState = pagerState,
            activeDotColor = MaterialTheme.colorScheme.primary,
            dotColor = Color.LightGray,
            dotCount = pagerState.pageCount,
            orientation = PagerIndicatorOrientation.Horizontal
        )

        Button(
            onClick = {
                scope.launch {
                    if (pagerState.currentPage == pagerState.pageCount - 1) {
                        onGetStarted()
                        return@launch
                    }

                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .height(ButtonDefaults.MinHeight.times(1.2f))
        ) {
            Text(text = stringResource(if (pagerState.currentPage == pagerState.pageCount - 1) R.string.onboarding_get_started else R.string.onboarding_next))
        }

        Spacer(Modifier.height(32.dp))
    }

}

@Composable
private fun Page(
    painter: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painter,
            contentDescription = null
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.92f)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.92f)
        )
    }
}
