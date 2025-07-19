package com.anafthdev.bentalatumblr.ui.home

import android.icu.text.SimpleDateFormat
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.data.Destinations
import com.anafthdev.bentalatumblr.data.datasource.local.LocalDrinkBottleDataProvider
import com.anafthdev.bentalatumblr.data.datasource.local.LocalDrinkHistoryDataProvider
import com.anafthdev.bentalatumblr.data.datasource.local.LocalUserProfileDataProvider
import com.anafthdev.bentalatumblr.data.enums.TrackingMethod
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.foundation.component.AcceleratingIconButton
import com.anafthdev.bentalatumblr.foundation.component.DrinkHistoryItem
import com.anafthdev.bentalatumblr.foundation.component.DrinkHistoryReminderItem
import com.anafthdev.bentalatumblr.foundation.component.TimePicker
import com.anafthdev.bentalatumblr.foundation.component.WaveAnimation
import com.anafthdev.bentalatumblr.foundation.extension.asInteger
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTheme
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import com.anafthdev.bentalatumblr.ui.app.BottomNavigationBar
import com.anafthdev.datemodule.DateModule
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.random.Random

@Composable
@Preview(showSystemUi = true)
private fun HomeScreenPreview() {
    BentalaTumblrTheme {
        Scaffold(
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    modifier = Modifier
                        .offset(y = 40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    visible = true,
                    selectedDestination = null,
                    onDestinationSelected = {

                    }
                )
            }
        ) { scaffoldPadding ->
            HomeScreenContent(
                contentPadding = PaddingValues(16.dp),
                state = HomeState(
                    userProfile = LocalUserProfileDataProvider.dinaSartika,
                    drinkHistories = LocalDrinkHistoryDataProvider.values.toList()
                ),
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .statusBarsPadding()
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onNavigateTo: (Destinations) -> Unit = {},
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val lazyListState = rememberLazyListState()

    var drinkHistoryToEdit by rememberSaveable { mutableStateOf<DrinkHistory?>(null) }
    var showAddRecordBottomSheet by rememberSaveable { mutableStateOf(false) }
    val isScrolling by remember { derivedStateOf { lazyListState.isScrollInProgress } }

    AddRecordBottomSheet(
        visible = showAddRecordBottomSheet,
        drinkHistory = drinkHistoryToEdit,
        onConfirm = viewModel::saveHistory,
        onDismissRequest = {
            showAddRecordBottomSheet = false
        }
    )

    Surface(
        color = Color(0xFFF4F8FB)
    ) {
        Box {
            AnimatedVisibility(
                visible = !isScrolling,
                enter = scaleIn(tween(256)) + fadeIn(),
                exit = scaleOut(tween(256)) + fadeOut(),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
                    .zIndex(1f)
            ) {
                FloatingActionButton(
                    onClick = {
                        showAddRecordBottomSheet = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            }

            Scaffold(
                contentWindowInsets = WindowInsets.safeDrawing
            ) {
                HomeScreenContent(
                    state = state,
                    contentPadding = it,
                    lazyListState = lazyListState,
                    onDelete = viewModel::deleteHistory,
                    onExchangePointIconClick = {
                        onNavigateTo(Destinations.Marketplace)
                    },
                    onDrinkWaterClicked = {
                        viewModel.saveHistory(
                            DrinkHistory(
                                id = Random.nextInt(),
                                date = System.currentTimeMillis(),
                                goal = state.dailyGoal.toDouble(),
                                bottle = LocalDrinkBottleDataProvider.customBottle.copy(
                                    volume = 200.0
                                ),
                                trackingMethod = TrackingMethod.Manual
                            )
                        )
                    },
                    onFindMyBentalaClicked = {
                        onNavigateTo(Destinations.FindTumblr)
                    },
                    onEdit = {
                        drinkHistoryToEdit = it
                        showAddRecordBottomSheet = true
                    },
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxSize()
                )
            }
        }
    }

}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    onExchangePointIconClick: () -> Unit = {},
    onFindMyBentalaClicked: () -> Unit = {},
    onDrinkWaterClicked: () -> Unit = {},
    onNotificationIconClicked: () -> Unit = {},
    onDelete: (DrinkHistory) -> Unit = {},
    onEdit: (DrinkHistory) -> Unit = {}
) {

    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        item {
            Spacer(Modifier.height(16.dp))
        }

        item {
            Greeting(
                name = state.userProfile.name,
                onNotificationIconClicked = onNotificationIconClicked,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            PointCard(
                point = state.point,
                onExchangePointIconClick = onExchangePointIconClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            ReminderCard(
                drinkProgress = state.drinkProgress,
                dailyGoal = state.dailyGoal,
                historySize = state.drinkHistories.size,
                onDrinkWaterClicked = onDrinkWaterClicked,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

//        item {
//
//            val circleProgress by animateFloatAsState(
//                targetValue = state.drinkProgress.toFloat() / state.dailyGoal,
//                animationSpec = tween(256)
//            )
//
//            Row(
//                verticalAlignment = Alignment.Bottom,
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                CircleProgress(
//                    progress = { circleProgress },
//                    drinkProgress = state.drinkProgress,
//                    modifier = Modifier
//                        .size(169.dp)
//                )
//
//                ProgressCard(
//                    dailyGoal = state.dailyGoal,
//                    drinkProgress = state.drinkProgress,
//                    modifier = Modifier
//                        .weight(1f)
//                )
//            }
//        }

        item {
            HorizontalDivider()
        }

        item {
            Text(
                text = "Bottle Status",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        item {
            BottleStatusItem(
                icon = R.drawable.thermometer,
                title = R.string.home_temperature,
                value = "${state.bottleTemperature.toInt()}°C",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            BottleStatusItem(
                icon = R.drawable.battery,
                title = R.string.home_battery,
                value = "${state.bottleBattery.toInt()}%",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            BottleStatusItem(
                icon = R.drawable.water_level,
                title = R.string.home_remaining_water,
                value = "${state.remainingWater}ml",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            FilledTonalButton(
                shape = RoundedCornerShape(8.dp),
                contentPadding = ButtonDefaults.TextButtonWithIconContentPadding,
                onClick = onFindMyBentalaClicked,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_global_search),
                    contentDescription = null
                )

                Spacer(Modifier.width(8.dp))

                Text("Find My Bentala")
            }
        }

        item {
            HorizontalDivider()
        }

        item {
            Text(
                text = stringResource(R.string.home_todays_history),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        item {
            DrinkHistoryReminderItem(
                timeInMillis = 1752814800000,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
            )
        }

        items(
            items = state.drinkHistories,
            key = { it.id }
        ) { history ->
            DrinkHistoryItem(
                history = history,
                onDelete = { onDelete(history) },
                onEdit = { onEdit(history) },
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem()
            )
        }

        item {
            Spacer(Modifier
                .height(64.dp)
                .navigationBarsPadding())
        }
    }
}

@Composable
private fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onNotificationIconClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.jujingyi),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.home_greeting),
                style = MaterialTheme.typography.labelMedium,
                color = BentalaTheme.colorScheme.lightTextColor
            )

            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
            )
        }

        FilledIconButton(
            onClick = onNotificationIconClicked,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.White
            )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_notification_filled),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun PointCard(
    point: Int,
    modifier: Modifier = Modifier,
    onExchangePointIconClick: () -> Unit = {}
) {

    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.coin_anim)
    )

    val lottieProgress by animateLottieCompositionAsState(
        composition = lottieComposition,
        reverseOnRepeat = true,
        iterations = Int.MAX_VALUE
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                        .border(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape,
                            width = 1.dp
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_bentala_logo_only),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                        )

                        Text(
                            text = stringResource(R.string.point_card_n_point, point),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                LottieAnimation(
                    composition = lottieComposition,
                    progress = { lottieProgress },
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .height(64.dp)
                )
            }

            HorizontalDivider()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.point_card_exchange_point),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .weight(1f)
                )

                IconButton(
                    onClick = onExchangePointIconClick
                ) { 
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }

}

/**
 * @param nextReminder for example "13:00:
 */
@Composable
private fun ReminderCard(
    drinkProgress: Int,
    dailyGoal: Int,
    historySize: Int,
    modifier: Modifier = Modifier,
    onDrinkWaterClicked: () -> Unit
) {

    val circleProgress by animateFloatAsState(
        targetValue = drinkProgress.toFloat() / dailyGoal,
        animationSpec = tween(256)
    )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .zIndex(1f)
            ) {
                Text(
                    text = "Daily Drink Target",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "${drinkProgress}ml water ($historySize glass)",
                    style = MaterialTheme.typography.labelLarge,
                    color = BentalaTheme.colorScheme.lightTextColor,
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )

                Button(
                    onClick = onDrinkWaterClicked,
                ) {
                    Text(
                        text = "Drink 200ml",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

//            Image(
//                painter = painterResource(R.drawable.challenge_card_water_drop),
//                contentDescription = null,
//                modifier = Modifier
//                    .scale(2.2f)
//                    .align(Alignment.BottomEnd)
//                    .zIndex(1f)
//                    .offset((-16).dp, (-8).dp)
//            )

//            CircleProgress(
//                progress = { circleProgress },
//                drinkProgress = drinkProgress,
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(128.dp)
//                    .zIndex(1f)
//                    .align(Alignment.CenterEnd)
//            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .zIndex(1f)
                    .align(Alignment.CenterEnd)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "${dailyGoal}ml",
                        style = MaterialTheme.typography.titleSmall,
                    )

                    Text(
                        text = "${drinkProgress}ml",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }

                CircularProgressIndicator(
                    progress = { circleProgress },
                    strokeWidth = 8.dp,
                    modifier = Modifier
                        .size(128.dp)
                )
            }

            WaveAnimation(
                color = Color(0xFF5DCCFC).copy(0.5f),
                amplitude = 24f,
                frequency = 0.01f,
                animationSpeed = 5000,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(169.dp)
                    .align(Alignment.BottomCenter)
            )

            WaveAnimation(
                color = Color(0xFF5DCCFC).copy(0.5f),
                amplitude = 24f,
                frequency = 0.01f,
                animationSpeed = 4000,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(169.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun CircleProgress(
    progress: () -> Float,
    drinkProgress: Int,
    modifier: Modifier = Modifier
) {

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = 6.dp,
                color = Color(0xFFADE5FC),
                shape = CircleShape
            )
    ) {
        WaveAnimation(
            progress = progress(),
            color = Color(0xFF5DCCFC).copy(0.5f),
            amplitude = 24f,
            frequency = 0.01f,
            animationSpeed = 5000,
            modifier = Modifier
                .matchParentSize()
                .height(128.dp)
                .align(Alignment.BottomCenter)
        )

        WaveAnimation(
            progress = progress(),
            color = Color(0xFF5DCCFC).copy(0.5f),
            amplitude = 24f,
            frequency = 0.01f,
            animationSpeed = 4000,
            modifier = Modifier
                .matchParentSize()
                .height(128.dp)
                .align(Alignment.BottomCenter)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
                text = "Today's Progress",
                style = MaterialTheme.typography.labelSmall
            )

            Text(
                text = "${drinkProgress}ml",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ProgressCard(
    dailyGoal: Int,
    drinkProgress: Int,
    modifier: Modifier = Modifier
) {

    val progress by animateFloatAsState(
        targetValue = drinkProgress.toFloat() / dailyGoal.toFloat(),
        animationSpec = tween(256)
    )

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Target",
                style = MaterialTheme.typography.labelMedium,
                color = BentalaTheme.colorScheme.lightTextColor
            )

            Text(
                text = "${dailyGoal}ml",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                )

                Text(
                    text = "${drinkProgress-dailyGoal}ml",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            LinearProgressIndicator(
                progress = { progress },
                drawStopIndicator = {},
                color = BentalaTheme.colorScheme.primary,
                trackColor = BentalaTheme.colorScheme.primary.copy(0.24f),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun BottleStatusItem(
    @DrawableRes icon: Int,
    @StringRes title: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE8EDF2))
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
            )
        }

        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .weight(1f)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddRecordBottomSheet(
    visible: Boolean,
    modifier: Modifier = Modifier,
    drinkHistory: DrinkHistory? = null,
    onDismissRequest: () -> Unit,
    onConfirm: (DrinkHistory) -> Unit
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Kenapa pakeknya string?, supaya user bisa bebas ngubah2 volume lewat textfield
    // biar gak ada bug, tapi nanti kalo angkanya gak valid, baru kasih error
    var volume by rememberSaveable { mutableStateOf("100") }
    var time by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    val formattedTime = remember(context, configuration, time) {
        val date = when {
            DateModule.isToday(time) -> context.getString(R.string.home_today)
            DateModule.isYesterday(time) -> context.getString(R.string.home_yesterday)
            else -> SimpleDateFormat("EEEE, dd MMMM yyyy", configuration.locales[0]).format(time)
        }

        val time = SimpleDateFormat("HH:mm", configuration.locales[0]).format(time)

        "$date, $time"
    }

    val hideSheet: () -> Unit = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) onDismissRequest()
        }
    }

    val checkIsVolumeNotValid: (String) -> Boolean = {
        val parsed = it.toFloatOrNull()

        isError = parsed == null
        isError
    }

    LaunchedEffect(drinkHistory) {
        if (drinkHistory != null) {
            volume = drinkHistory.bottle.volume.toString()
            time = drinkHistory.date
        }
    }

    if (showTimePicker) {
        TimePicker(
            onDismiss = { showTimePicker = false },
            onConfirm = { timePickerState ->
                val instant = Instant.ofEpochMilli(time)
                
                // Apply zone ke date time
                val zonedDateTime = instant.atZone(ZoneId.systemDefault())
                    .withHour(timePickerState.hour)
                    .withMinute(timePickerState.minute)

                // ubah zona waktu ke UTC
                time = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toInstant().toEpochMilli()
                showTimePicker = false
            }
        )
    }

    if (visible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Tambah Asupan Minum",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .weight(1f)
                    )

                    IconButton(
                        onClick = hideSheet
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null
                        )
                    }
                }

                HorizontalDivider()

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.home_amount),
                        style = MaterialTheme.typography.labelLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        AcceleratingIconButton(
                            onClick = {
                                if (checkIsVolumeNotValid(volume)) return@AcceleratingIconButton
                                volume = volume.toFloat().plus(10f).asInteger().toString()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null
                            )
                        }

                        OutlinedTextField(
                            value = volume,
                            singleLine = true,
                            isError = isError,
                            colors = TextFieldDefaults.colors(
                                disabledIndicatorColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            suffix = {
                                Text(
                                    text = "ml"
                                )
                            },
                            supportingText = {
                                if (isError) {
                                    Text(
                                        text = stringResource(R.string.home_invalid_volume),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            onValueChange = { newValue ->
                                if (newValue.length >= 5) return@OutlinedTextField
                                volume = newValue
                                checkIsVolumeNotValid(newValue)
                            },
                            modifier = Modifier
                                .widthIn(1.dp, Dp.Infinity)
                        )

                        AcceleratingIconButton(
                            onClick = {
                                if (checkIsVolumeNotValid(volume)) return@AcceleratingIconButton
                                val numVolume = volume.toFloat().minus(10f)
                                if (numVolume <= 0) return@AcceleratingIconButton
                                volume = numVolume.asInteger().toString()
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp, 2.dp)
                                    .clip(CircleShape)
                                    .background(LocalContentColor.current)
                            )
                        }
                    }

                    Text(
                        text = stringResource(R.string.home_time),
                        style = MaterialTheme.typography.labelLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = formattedTime,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .weight(1f)
                        )

                        IconButton(
                            onClick = {
                                showTimePicker = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarMonth,
                                contentDescription = null
                            )
                        }
                    }
                }

                HorizontalDivider()

                Button(
                    onClick = {
                        if (checkIsVolumeNotValid(volume)) return@Button

                        val history = drinkHistory?.copy(
                            bottle = drinkHistory.bottle.copy(volume = volume.toDouble()),
                            date = time
                        ) ?: DrinkHistory(
                            id = Random.nextInt(),
                            date = time,
                            goal = 0.0,
                            bottle = LocalDrinkBottleDataProvider.customBottle.copy(
                                volume = volume.toDouble()
                            ),
                            trackingMethod = TrackingMethod.Manual
                        )

                        onConfirm(history)

                        hideSheet()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ButtonDefaults.MinHeight + 8.dp)
                ) {
                    Text(stringResource(R.string.home_save))
                }
            }
        }
    }
}
