package com.anafthdev.bentalatumblr.ui.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.anafthdev.bentalatumblr.data.BottomNavigationBarItem
import com.anafthdev.bentalatumblr.data.Destinations
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import com.anafthdev.bentalatumblr.ui.auth.login.LoginScreen
import com.anafthdev.bentalatumblr.ui.auth.onboarding.OnboardingScreen
import com.anafthdev.bentalatumblr.ui.find_tumblr.FindTumblrScreen
import com.anafthdev.bentalatumblr.ui.home.HomeScreen
import com.anafthdev.bentalatumblr.ui.marketplace.MarketplaceScreen
import com.anafthdev.bentalatumblr.ui.mission.MissionScreen
import com.anafthdev.bentalatumblr.ui.reminder.ReminderScreen
import com.anafthdev.bentalatumblr.ui.setting.SettingScreen
import com.anafthdev.bentalatumblr.ui.statistic.StatisticScreen

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun BottomNavBarPreview() {
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
            Box(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun BentalaApp(
    isFirstInstall: Boolean,
    isLoggedIn: Boolean
) {

    val navController = rememberNavController()
    val currentDestination by navController.currentBackStackEntryAsState()

    val isNavigationBarVisible = remember(currentDestination) {
        Destinations.bottomNavigationBarItems.any { navBarItem ->
            currentDestination?.destination?.hierarchy?.any { destination ->
                navBarItem.destinations?.let { destination.hasRoute(it::class) } ?: false
            } == true
        }
    }

    BentalaTumblrTheme {
        Scaffold(
            contentWindowInsets = WindowInsets(0),
            bottomBar = {
                if (isNavigationBarVisible) {
                    BottomNavigationBar(
                        visible = true,
                        selectedDestination = currentDestination?.destination,
                        onDestinationSelected = { navigationBarItem ->
                            navigationBarItem.destinations?.let {
                                navController.navigate(it) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        }
                    )
                }
            }
        ) { scaffoldPadding ->
            NavHost(
                navController = navController,
                startDestination = when {
                    isFirstInstall -> Destinations.Auth.Onboarding
                    !isLoggedIn -> Destinations.Auth.Login
                    else -> Destinations.Home
                },
                modifier = Modifier
                    .padding(scaffoldPadding)
            ) {
                composable<Destinations.Auth.Onboarding> { backEntry ->
                    OnboardingScreen(
                        viewModel = hiltViewModel(backEntry),
                        onGetStarted = {
                            navController.navigate(Destinations.Auth.Login) {
                                popUpTo(Destinations.Auth.Onboarding) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable<Destinations.Auth.Login> { backEntry ->
                    LoginScreen(
                        viewModel = hiltViewModel(backEntry),
                        onLoggedIn = {
                            navController.navigate(Destinations.Home) {
                                popUpTo(Destinations.Auth.Login) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable<Destinations.Home> { backEntry ->
                    HomeScreen(
                        viewModel = hiltViewModel(backEntry),
                        onNavigateTo = { dest ->
                            navController.navigate(dest)
                        }
                    )
                }

                composable<Destinations.Statistic> { backEntry ->
                    StatisticScreen(
                        viewModel = hiltViewModel(backEntry)
                    )
                }

                composable<Destinations.Mission> { backEntry ->
                    MissionScreen(
                        viewModel = hiltViewModel(backEntry)
                    )
                }

                composable<Destinations.Profile> { backEntry ->
                    SettingScreen(
                        viewModel = hiltViewModel(backEntry),
                        onNavigateTo = { dest ->
                            navController.navigate(dest)
                        }
                    )
                }

                composable<Destinations.FindTumblr> { backEntry ->
                    FindTumblrScreen(
                        viewModel = hiltViewModel(backEntry),
                        onNavigationIconClick = navController::navigateUp
                    )
                }

                composable<Destinations.Reminder> { backEntry ->
                    ReminderScreen(
                        viewModel = hiltViewModel(backEntry),
                        onNavigationIconClick = navController::navigateUp
                    )
                }

                composable<Destinations.Marketplace> { backEntry ->
                    MarketplaceScreen(
                        viewModel = hiltViewModel(backEntry),
                        onNavigationIconClick = navController::navigateUp
                    )
                }
            }
        }
    }

}

@Composable
fun BottomNavigationBar(
    visible: Boolean,
    selectedDestination: NavDestination?,
    modifier: Modifier = Modifier,
    onDestinationSelected: (BottomNavigationBarItem) -> Unit
) {

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(tween(256)) + fadeIn(),
        exit = slideOutVertically(tween(256)) + fadeOut(),
    ) {
        Column {
            HorizontalDivider()

            NavigationBar(
                modifier = modifier,
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Destinations.bottomNavigationBarItems.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        enabled = destination.destinations != null,
                        selected = destination.destinations?.let { selectedDestination?.hasRoute(it::class) } ?: false,
                        onClick = {
                            onDestinationSelected(destination)
                        },
                        icon = {
                            destination.icon?.let { id ->
                                Icon(
                                    painter = painterResource(id),
                                    contentDescription = null
                                )
                            }
                        },
                        label = {
                            destination.label?.let { id ->
                                Text(stringResource(id))
                            }
                        }
                    )
                }
            }
        }
    }

}
