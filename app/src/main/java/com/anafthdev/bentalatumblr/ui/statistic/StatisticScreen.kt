package com.anafthdev.bentalatumblr.ui.statistic

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import android.icu.util.Calendar
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.data.StatisticTimeType
import com.anafthdev.bentalatumblr.foundation.extension.ignoreHorizontalParentPadding
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import com.anafthdev.bentalatumblr.foundation.theme.graphDown
import com.anafthdev.bentalatumblr.foundation.theme.graphUp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.MarkerCorneredShape
import java.text.SimpleDateFormat
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
@Preview(showSystemUi = false, showBackground = true)
private fun TimeSelectorPreview() {
    BentalaTumblrTheme {
        Column {
            TimeSelector(
                startDate = System.currentTimeMillis(),
                endDate = System.currentTimeMillis(),
                statisticTimeType = StatisticTimeType.Daily,
                modifier = Modifier
            )

            TimeSelector(
                startDate = System.currentTimeMillis() - 1000 * 60 * 60 * 48,
                endDate = System.currentTimeMillis(),
                statisticTimeType = StatisticTimeType.Daily,
                modifier = Modifier
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun StatisticScreenPreview() {
    BentalaTumblrTheme {
        Scaffold(
            contentWindowInsets = WindowInsets(0),
            topBar = {
                StatisticTopAppBar(
                    selectedTimeType = StatisticTimeType.Daily,
                )
            }
        ) {
            StatisticScreenContent(
                state = StatisticState(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                onDateChange = { _, _ -> },
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StatisticScreen(
    viewModel: StatisticViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 1)
        }

        val startDate = cal.timeInMillis
        val endDate = cal.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        viewModel.setInitialDateRange(startDate, endDate)
    }

    Surface(
        color = Color(0xFFF4F8FB)
    ) {
        Scaffold(
            topBar = {
                StatisticTopAppBar(
                    selectedTimeType = state.statisticTimeType,
                    onTimeTypeChange = viewModel::setStatisticTimeType
                )
            }
        ) { scaffoldPadding ->
            StatisticScreenContent(
                state = state,
                contentPadding = PaddingValues(16.dp),
                onDateChange = viewModel::setDateRange,
                onCompareDateChange = viewModel::setCompareDateRange,
                onPremiumBannerClick = {},
                modifier = modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatisticScreenContent(
    state: StatisticState,
    contentPadding: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier,
    onDateChange: (Long, Long) -> Unit = { _, _ -> },
    onCompareDateChange: (Long, Long) -> Unit = { _, _ -> },
    onPremiumBannerClick: () -> Unit = {}
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
//        item {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//            ) {
//                Text(
//                    text = "Daily intake",
//                    style = MaterialTheme.typography.bodyMedium,
//                )
//
//                Text(
//                    text = "1400ml",
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Text(
//                    text = buildAnnotatedString {
//                        withStyle(
//                            style = MaterialTheme.typography.labelMedium.toSpanStyle()
//                        ) {
//                            append("Today ")
//                        }
//
//                        withStyle(
//                            style = MaterialTheme.typography.labelMedium.toSpanStyle().copy(
//                                color = Color.Green
//                            )
//                        ) {
//                            append("+10%")
//                        }
//                    }
//                )
//            }
//        }
        
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TimeSelector(
                    startDate = state.endDate,
                    endDate = state.startDate,
                    statisticTimeType = state.statisticTimeType,
                    onDateChange = onDateChange,
                )
            }
        }

        item {
            AnimatedContent(
                targetState = state.statisticTimeType,
            ) { timeType ->
                if (timeType == StatisticTimeType.Daily) {
                    LineChart(
                        state = state
                    )
                } else {
                    BarChart(
                        state = state
                    )
                }
            }
        }

        if (state.statisticTimeType != StatisticTimeType.Daily) {
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        RowItem(
                            title = stringResource(R.string.statistic_avg_volume),
                            value = stringResource(
                                id = R.string.statistic_avg_volume_value,
                                state.averageVolume.toInt(),
                                "ml"
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        HorizontalDivider()

                        RowItem(
                            title = stringResource(R.string.statistic_avg_completion),
                            value = "${state.averageCompletion}%",
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        HorizontalDivider()

                        RowItem(
                            title = stringResource(R.string.statistic_drink_frequency),
                            value = stringResource(
                                R.string.statistic_drink_frequency_value,
                                state.drinkFrequency
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }

        if (!state.userProfile.isPremium) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .ignoreHorizontalParentPadding(16.dp)
                        .clickable { onPremiumBannerClick() }
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.tertiaryContainer,
                                    MaterialTheme.colorScheme.primaryContainer,
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.premium_diamond),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )

                    Text(
                        text = stringResource(R.string.statistic_premium_text),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer {
                            alpha = if (state.userProfile.isPremium) 1f else 0.48f
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (!state.userProfile.isPremium) {
                            Image(
                                painter = painterResource(R.drawable.premium_diamond),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }

                        Text(
                            text = buildAnnotatedString {
                                append(
                                    stringResource(
                                        id = when (state.statisticTimeType) {
                                            StatisticTimeType.Daily -> R.string.statistic_highest_daily_record
                                            StatisticTimeType.Weekly -> R.string.statistic_highest_weekly_record
                                            StatisticTimeType.Monthly -> R.string.statistic_highest_monthly_record
                                            StatisticTimeType.Yearly -> R.string.statistic_highest_yearly_record
                                        }
                                    )
                                )

                                if (!state.userProfile.isPremium) {
                                    append(" (${stringResource(R.string.statistic_example)})")
                                }
                            },
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "25/06/2025",
                            style = MaterialTheme.typography.bodyMedium,
                        )

                        Text(
                            text = "2100ml",
                            style = MaterialTheme.typography.bodyMedium,
                        )

                        Text(
                            text = stringResource(R.string.statistic_n_times, 11),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        item {
            val dailyFormatter = remember {
                DateFormat.getDateInstance(DateFormat.MEDIUM)
            }

            val monthFormatter = remember {
                SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            }

            val yearFormatter = remember {
                SimpleDateFormat("yyyy", Locale.getDefault())
            }

            val volumeDiff = remember(state.totalVolume, state.totalVolumeCompare) {
                state.totalVolume - state.totalVolumeCompare
            }

            val totalVolume = remember(state.totalVolume) {
                "${String.format(" % .2f", state.totalVolume / 1000)}L"
            }

            val totalVolumeComparison = remember(state.totalVolume, state.totalVolumeCompare, volumeDiff) {
                if (volumeDiff > 0) "▲ +${String.format("%.2f", volumeDiff / 1000)}L"
                else if (volumeDiff < 0) "▼ ${String.format("%.2f", volumeDiff / 1000)}L"
                else "▲ +0ml"
            }

            val completionRateComparison = remember(state.averageCompletion, state.averageCompletionCompare) {
                val diff = state.averageCompletion - state.averageCompletionCompare

                if (diff > 0) "▲ +$diff%"
                else if (diff < 0) "▼ $diff%"
                else "▲ +0%"
            }
//            val useCaseState = rememberUseCaseState()
//
//            val selection = remember(state.statisticTimeType) {
//                when (state.statisticTimeType) {
//                    StatisticTimeType.Daily -> CalendarSelection.Date { newDate ->
//                        val calendar = Calendar.getInstance().apply {
//                            timeInMillis = newDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
//
//                            set(Calendar.HOUR_OF_DAY, 0)
//                            set(Calendar.MINUTE, 0)
//                            set(Calendar.SECOND, 0)
//                            set(Calendar.MILLISECOND, 1)
//                        }
//
//                        val startDate = calendar.timeInMillis
//                        val endDate = calendar.apply {
//                            set(Calendar.HOUR_OF_DAY, 23)
//                            set(Calendar.MINUTE, 59)
//                            set(Calendar.SECOND, 59)
//                            set(Calendar.MILLISECOND, 999)
//                        }.timeInMillis
//
//                        onCompareDateChange(startDate, endDate)
//                    }
//                    StatisticTimeType.Weekly -> CalendarSelection.Dates { newDate ->
//
//                    }
//                    StatisticTimeType.Monthly -> CalendarSelection.Date { newDate ->
//                        val calendar = Calendar.getInstance().apply {
//                            timeInMillis = newDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
//
//                            set(Calendar.HOUR_OF_DAY, 0)
//                            set(Calendar.MINUTE, 0)
//                            set(Calendar.SECOND, 0)
//                            set(Calendar.MILLISECOND, 1)
//                        }
//
//                        val startDate = calendar.timeInMillis
//                        val endDate = calendar.apply {
//                            set(Calendar.HOUR_OF_DAY, 23)
//                            set(Calendar.MINUTE, 59)
//                            set(Calendar.SECOND, 59)
//                            set(Calendar.MILLISECOND, 999)
//                        }.timeInMillis
//
//                        onCompareDateChange(startDate, endDate)
//                    }
//                    StatisticTimeType.Yearly -> CalendarSelection.Date { newDate ->
//
//                    }
//                }
//            }
//
//            CalendarDialog(
//                state = useCaseState,
//                config = CalendarConfig(
//                    yearSelection = true,
//                    monthSelection = true,
//                    style = CalendarStyle.MONTH
//                ),
//                selection = selection
//            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer {
                            alpha = if (state.userProfile.isPremium) 1f else 0.48f
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (!state.userProfile.isPremium) {
                            Image(
                                painter = painterResource(R.drawable.premium_diamond),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }

                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(R.string.statistic_compare_hydration))

                                if (!state.userProfile.isPremium) {
                                    append(" (${stringResource(R.string.statistic_example)})")
                                }
                            },
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = buildAnnotatedString {
//                                append(stringResource(
//                                    id = when (state.statisticTimeType) {
//                                        StatisticTimeType.Daily -> R.string.statistic_today
//                                        StatisticTimeType.Weekly -> R.string.statistic_this_week
//                                        StatisticTimeType.Monthly -> R.string.statistic_this_month
//                                        StatisticTimeType.Yearly -> R.string.statistic_this_year
//                                    }
//                                ))

                                append(
                                    when (state.statisticTimeType) {
                                        StatisticTimeType.Daily -> dailyFormatter.format(state.startDate)
                                        StatisticTimeType.Weekly -> "Weekly comparation not implemented yet"
                                        StatisticTimeType.Monthly -> monthFormatter.format(state.startDate)
                                        StatisticTimeType.Yearly -> yearFormatter.format(state.startDate)
                                    }
                                )

                                append(" ${stringResource(R.string.statistic_vs)} ")

                                append(
                                    when (state.statisticTimeType) {
                                        StatisticTimeType.Daily -> dailyFormatter.format(state.startDateCompare)
                                        StatisticTimeType.Weekly -> "Weekly comparation not implemented yet"
                                        StatisticTimeType.Monthly -> monthFormatter.format(state.startDateCompare)
                                        StatisticTimeType.Yearly -> yearFormatter.format(state.startDateCompare)
                                    }
                                )
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )

                        IconButton(
                            enabled = state.userProfile.isPremium,
                            onClick = {
//                                useCaseState.show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowRight,
                                contentDescription = null
                            )
                        }
                    }

                    LineChartCompare(
                        state = state
                    )

                    Text(
                        text = stringResource(
                            id = when (state.statisticTimeType) {
                                StatisticTimeType.Daily -> R.string.statistic_today
                                StatisticTimeType.Weekly -> R.string.statistic_this_week
                                StatisticTimeType.Monthly -> R.string.statistic_this_month
                                StatisticTimeType.Yearly -> R.string.statistic_this_year
                            }
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    ComparisonItem(
                        title = stringResource(R.string.statistic_volume),
                        value = totalVolume,
                        comparisonValue = totalVolumeComparison,
                        positive = volumeDiff >= 0
                    )

                    ComparisonItem(
                        title = stringResource(R.string.statistic_completion_rate),
                        value = "${state.averageCompletion}%",
                        comparisonValue = completionRateComparison,
                        positive = state.averageCompletion >= state.averageCompletionCompare
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatisticTopAppBar(
    selectedTimeType: StatisticTimeType,
    modifier: Modifier = Modifier,
    onTimeTypeChange: (StatisticTimeType) -> Unit = {}
) {

    var isExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier,
        title = {
            Text(stringResource(selectedTimeType.titleResId))
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    isExpanded = !isExpanded
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = null
                )
            }

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                StatisticTimeType.entries.forEach { timeType ->
                    DropdownMenuItem(
                        text = { Text(stringResource(timeType.titleResId)) },
                        onClick = {
                            isExpanded = false
                            onTimeTypeChange(timeType)
                        }
                    )
                }
            }
        },
        actions = {

        }
    )
}

@Composable
private fun TimeSelector(
    startDate: Long,
    endDate: Long,
    statisticTimeType: StatisticTimeType,
    modifier: Modifier = Modifier,
    onDateChange: (Long, Long) -> Unit = { _, _ -> }
) {
    // TODO: Manipulasi waktunya buat next/prev

    val formattedStartDate = remember(statisticTimeType, startDate) {
        when (statisticTimeType) {
            StatisticTimeType.Daily -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(startDate)
            StatisticTimeType.Weekly -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(startDate)
            StatisticTimeType.Monthly -> SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(startDate)
            StatisticTimeType.Yearly -> SimpleDateFormat("yyyy", Locale.getDefault()).format(startDate)
        }
    }

    val formattedEndDate = remember(statisticTimeType, endDate) {
        when (statisticTimeType) {
            StatisticTimeType.Daily -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(endDate)
            StatisticTimeType.Weekly -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(endDate)
            StatisticTimeType.Monthly -> SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(endDate)
            StatisticTimeType.Yearly -> SimpleDateFormat("yyyy", Locale.getDefault()).format(endDate)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = startDate

                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 100)
                }

                when (statisticTimeType) {
                    StatisticTimeType.Daily -> {
                        calendar.add(Calendar.DAY_OF_MONTH, -1)

                        val previousStartDate = calendar.timeInMillis

                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, 23)
                            set(Calendar.MINUTE, 59)
                            set(Calendar.SECOND, 59)
                            set(Calendar.MILLISECOND, 900)
                        }

                        val previousEndDate = calendar.timeInMillis

                        onDateChange(previousStartDate, previousEndDate)
                    }
                    StatisticTimeType.Weekly -> {

                    }
                    StatisticTimeType.Monthly -> {
                        calendar.add(Calendar.MONTH, -1)

                        val previousStartDate = calendar.timeInMillis

                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, 23)
                            set(Calendar.MINUTE, 59)
                            set(Calendar.SECOND, 59)
                            set(Calendar.MILLISECOND, 900)
                        }

                        val previousEndDate = calendar.timeInMillis

                        onDateChange(previousStartDate, previousEndDate)
                    }
                    StatisticTimeType.Yearly -> {
                        calendar.add(Calendar.YEAR, -1)

                        val previousStartDate = calendar.timeInMillis

                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, 23)
                            set(Calendar.MINUTE, 59)
                            set(Calendar.SECOND, 59)
                            set(Calendar.MILLISECOND, 900)
                        }

                        val previousEndDate = calendar.timeInMillis

                        onDateChange(previousStartDate, previousEndDate)
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = null
            )
        }

        Text(
            text = buildAnnotatedString {
                append(formattedStartDate)
                if (formattedStartDate != formattedEndDate) {
                    append(" - ")
                    append(formattedEndDate)
                }
            },
            style = MaterialTheme.typography.bodyLarge
        )

        IconButton(
            onClick = {
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = startDate

                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 100)
                }

                when (statisticTimeType) {
                    StatisticTimeType.Daily -> {
                        calendar.add(Calendar.DAY_OF_MONTH, 1)

                        val previousStartDate = calendar.timeInMillis

                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, 23)
                            set(Calendar.MINUTE, 59)
                            set(Calendar.SECOND, 59)
                            set(Calendar.MILLISECOND, 900)
                        }

                        val previousEndDate = calendar.timeInMillis

                        onDateChange(previousStartDate, previousEndDate)
                    }
                    StatisticTimeType.Weekly -> {

                    }
                    StatisticTimeType.Monthly -> {
                        calendar.add(Calendar.MONTH, 1)

                        val previousStartDate = calendar.timeInMillis

                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, 23)
                            set(Calendar.MINUTE, 59)
                            set(Calendar.SECOND, 59)
                            set(Calendar.MILLISECOND, 900)
                        }

                        val previousEndDate = calendar.timeInMillis

                        onDateChange(previousStartDate, previousEndDate)
                    }
                    StatisticTimeType.Yearly -> {
                        calendar.add(Calendar.YEAR, 1)

                        val previousStartDate = calendar.timeInMillis

                        calendar.apply {
                            set(Calendar.HOUR_OF_DAY, 23)
                            set(Calendar.MINUTE, 59)
                            set(Calendar.SECOND, 59)
                            set(Calendar.MILLISECOND, 900)
                        }

                        val previousEndDate = calendar.timeInMillis

                        onDateChange(previousStartDate, previousEndDate)
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun BarChart(
    state: StatisticState,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current

    val monthsXLabel = remember(configuration.locales.get(0)) {
        getLocalizedMonthShortNames(configuration.locales.get(0))
    }

    val modelProducer = remember { CartesianChartModelProducer() }
    val columnCartesianLayer = rememberColumnCartesianLayer(
        rangeProvider = remember {
            CartesianLayerRangeProvider.fixed(minY = 0.0, maxY = 100.0)
        }
    )

    val chart = rememberCartesianChart(
        columnCartesianLayer,
        startAxis = VerticalAxis.rememberStart(
            guideline = null,
            valueFormatter = object : CartesianValueFormatter {
                override fun format(
                    context: CartesianMeasuringContext,
                    value: Double,
                    verticalAxisPosition: Axis.Position.Vertical?
                ): CharSequence {
                    return "${value.toInt()}%"
                }
            },
            itemPlacer = remember {
                VerticalAxis.ItemPlacer.step(
                    step = { 10.0 },
                )
            },
            label = rememberAxisLabelComponent(
                color = MaterialTheme.colorScheme.onBackground
            )
        ),
        bottomAxis = HorizontalAxis.rememberBottom(
            guideline = null,
            valueFormatter = when (state.statisticTimeType) {
                StatisticTimeType.Daily -> DailyBottomAxisValueFormatter
                StatisticTimeType.Weekly -> CartesianValueFormatter.Default
                StatisticTimeType.Monthly -> MonthlyBottomAxisValueFormatter
                StatisticTimeType.Yearly -> YearlyBottomAxisValueFormatter
                else -> CartesianValueFormatter.Default
            },
            label = rememberAxisLabelComponent(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    )

    LaunchedEffect(state.chartY, state.statisticTimeType) {
        modelProducer.runTransaction {
            if (state.statisticTimeType == StatisticTimeType.Yearly) {
                extras { it[YearlyBottomAxisLabelKey] = monthsXLabel }
            }

            columnSeries {
                series(state.chartY)
            }
        }
    }

    CartesianChartHost(
        chart = chart,
        modelProducer = modelProducer,
        modifier = modifier
            .heightIn(min = 256.dp)
    )
}

@Composable
private fun LineChart(
    state: StatisticState,
    modifier: Modifier = Modifier
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val lineCartesianLayer = rememberLineCartesianLayer(
        rangeProvider = remember {
            CartesianLayerRangeProvider.fixed(minY = 0.0, maxY = 100.0)
        },
        lineProvider = LineCartesianLayer.LineProvider.series(
            LineCartesianLayer.rememberLine(
                fill = LineCartesianLayer.LineFill.single(fill(MaterialTheme.colorScheme.primary)),
                areaFill = LineCartesianLayer.AreaFill.single(
                    fill(
                        ShaderProvider.verticalGradient(
                            arrayOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
                ),
                pointProvider = LineCartesianLayer.PointProvider.single(
                    LineCartesianLayer.Point(
                        component = rememberLineComponent(
                            fill = fill(MaterialTheme.colorScheme.primary),
                            shape = MarkerCorneredShape(
                                all = CorneredShape.Corner.Rounded,
                            )
                        )
                    )
                ),
                pointConnector = LineCartesianLayer.PointConnector.cubic(
                    curvature = 0.54f
                )
            )
        )
    )

    val chart = rememberCartesianChart(
        lineCartesianLayer,
        startAxis = VerticalAxis.rememberStart(
            guideline = null,
            valueFormatter = object : CartesianValueFormatter {
                override fun format(
                    context: CartesianMeasuringContext,
                    value: Double,
                    verticalAxisPosition: Axis.Position.Vertical?
                ): CharSequence {
                    return "${value.toInt()}%"
                }
            },
            itemPlacer = remember {
                VerticalAxis.ItemPlacer.step(
                    step = { 10.0 },
                )
            },
            label = rememberAxisLabelComponent(
                color = MaterialTheme.colorScheme.onBackground
            )
        ),
        bottomAxis = HorizontalAxis.rememberBottom(
            guideline = null,
            valueFormatter = when (state.statisticTimeType) {
                StatisticTimeType.Daily -> DailyBottomAxisValueFormatter
                StatisticTimeType.Weekly -> CartesianValueFormatter.Default
                StatisticTimeType.Monthly -> MonthlyBottomAxisValueFormatter
                StatisticTimeType.Yearly -> YearlyBottomAxisValueFormatter
                else -> CartesianValueFormatter.Default
            },
            label = rememberAxisLabelComponent(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    )

    LaunchedEffect(state.chartY) {
        modelProducer.runTransaction {
            lineSeries {
                series(state.chartY)
            }
        }
    }

    CartesianChartHost(
        chart = chart,
        modelProducer = modelProducer,
        modifier = modifier
            .heightIn(256.dp)
    )
}

@Composable
private fun LineChartCompare(
    state: StatisticState,
    modifier: Modifier = Modifier
) {

    val configuration = LocalConfiguration.current

    val monthsXLabel = remember(configuration.locales.get(0)) {
        getLocalizedMonthShortNames(configuration.locales.get(0))
    }

    val modelProducer = remember { CartesianChartModelProducer() }
    val lineCartesianLayer = rememberLineCartesianLayer(
        rangeProvider = remember {
            CartesianLayerRangeProvider.fixed(minY = 0.0, maxY = 100.0)
        },
        lineProvider = LineCartesianLayer.LineProvider.series(
            LineCartesianLayer.rememberLine(
                fill = LineCartesianLayer.LineFill.single(fill(MaterialTheme.colorScheme.primary)),
                areaFill = LineCartesianLayer.AreaFill.single(
                    fill(
                        ShaderProvider.verticalGradient(
                            arrayOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
                ),
                pointProvider = LineCartesianLayer.PointProvider.single(
                    LineCartesianLayer.Point(
                        component = rememberLineComponent(
                            fill = fill(MaterialTheme.colorScheme.primary),
                            shape = MarkerCorneredShape(
                                all = CorneredShape.Corner.Rounded,
                            )
                        )
                    )
                ),
                pointConnector = LineCartesianLayer.PointConnector.cubic(
                    curvature = 0.54f
                )
            ),
            LineCartesianLayer.rememberLine(
                fill = LineCartesianLayer.LineFill.single(fill(MaterialTheme.colorScheme.tertiary)),
                areaFill = LineCartesianLayer.AreaFill.single(
                    fill(
                        ShaderProvider.verticalGradient(
                            arrayOf(
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
                ),
                pointProvider = LineCartesianLayer.PointProvider.single(
                    LineCartesianLayer.Point(
                        component = rememberLineComponent(
                            fill = fill(MaterialTheme.colorScheme.tertiary),
                            shape = MarkerCorneredShape(
                                all = CorneredShape.Corner.Rounded,
                            )
                        )
                    )
                ),
                pointConnector = LineCartesianLayer.PointConnector.cubic(
                    curvature = 0.54f
                )
            )
        )
    )

    val chart = rememberCartesianChart(
        lineCartesianLayer,
        startAxis = VerticalAxis.rememberStart(
            guideline = null,
            valueFormatter = object : CartesianValueFormatter {
                override fun format(
                    context: CartesianMeasuringContext,
                    value: Double,
                    verticalAxisPosition: Axis.Position.Vertical?
                ): CharSequence {
                    return "${value.toInt()}%"
                }
            },
            itemPlacer = remember {
                VerticalAxis.ItemPlacer.step(
                    step = { 10.0 },
                )
            },
            label = rememberAxisLabelComponent(
                color = MaterialTheme.colorScheme.onBackground
            )
        ),
        bottomAxis = HorizontalAxis.rememberBottom(
            guideline = null,
            valueFormatter = when (state.statisticTimeType) {
                StatisticTimeType.Daily -> DailyBottomAxisValueFormatter
                StatisticTimeType.Weekly -> CartesianValueFormatter.Default
                StatisticTimeType.Monthly -> MonthlyBottomAxisValueFormatter
                StatisticTimeType.Yearly -> YearlyBottomAxisValueFormatter
                else -> CartesianValueFormatter.Default
            },
            label = rememberAxisLabelComponent(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    )

    LaunchedEffect(state.chartY, state.chartYCompare) {
        modelProducer.runTransaction {
            if (state.statisticTimeType == StatisticTimeType.Yearly) {
                extras { it[YearlyBottomAxisLabelKey] = monthsXLabel }
            }

            lineSeries {
                series(state.chartY)
                series(state.chartYCompare)
            }
        }
    }

    CartesianChartHost(
        chart = chart,
        modelProducer = modelProducer,
        modifier = modifier
            .heightIn(256.dp)
    )
}

@Composable
private fun ComparisonItem(
    title: String,
    value: String,
    comparisonValue: String,
    positive: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Text(
            text = "$title:",
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .weight(1f)
        )

        Text(
            text = comparisonValue,
            style = MaterialTheme.typography.bodyMedium,
            color = if (positive) graphUp else graphDown,
        )
    }
}

@Composable
private fun RowItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun getChartXLabels(selectedTimeType: StatisticTimeType): List<String> {
    // TODO: pake string localization
    return when (selectedTimeType) {
        StatisticTimeType.Daily -> listOf(
            "00:00", "01:00", "02:00", "03:00", "04:00",
            "05:00", "06:00", "07:00", "08:00", "09:00",
            "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00", "18:00", "19:00",
            "20:00", "21:00", "22:00", "23:00"
        )
        StatisticTimeType.Weekly -> listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        StatisticTimeType.Monthly -> listOf() // Pake index dari data y
        StatisticTimeType.Yearly -> listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )
    }
}

private fun getLocalizedMonthShortNames(locale: Locale): List<String> {
    return Month.entries.map { month ->
        month.getDisplayName(TextStyle.SHORT, locale)
    }
}

private val YearlyBottomAxisLabelKey = ExtraStore.Key<List<String>>()

private val YearlyBottomAxisValueFormatter = CartesianValueFormatter { context, x, _ ->
    val extras = context.model.extraStore.getOrNull(YearlyBottomAxisLabelKey)

    if (extras != null) extras[x.toInt()] else x.toInt().toString()
}

private val MonthlyBottomAxisValueFormatter = CartesianValueFormatter { context, x, _ ->
    x.toInt().plus(1).toString()
}

private val DailyBottomAxisValueFormatter = CartesianValueFormatter { context, x, _ ->
    String.format("%02d:00", x.toInt())

}
