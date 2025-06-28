package com.anafthdev.bentalatumblr.ui.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anafthdev.bentalatumblr.data.BottomNavigationBarItem
import com.anafthdev.bentalatumblr.data.Destinations
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import com.anafthdev.bentalatumblr.ui.home.HomeScreen

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
                    selectedDestination = 0,
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
fun BentalaApp() {

    val navController = rememberNavController()

    BentalaTumblrTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    visible = true,
                    selectedDestination = 0,
                    onDestinationSelected = {

                    }
                )
            }
        ) { scaffoldPadding ->
            NavHost(
                navController = navController,
                startDestination = Destinations.Home,
                modifier = Modifier
                    .padding(scaffoldPadding)
            ) {
                composable<Destinations.Home> { backEntry ->
                    HomeScreen(
                        viewModel = hiltViewModel(backEntry)
                    )
                }
            }
        }
    }

}

@Composable
fun BottomNavigationBar(
    visible: Boolean,
    selectedDestination: Int,
    modifier: Modifier = Modifier,
    onDestinationSelected: (BottomNavigationBarItem) -> Unit
) {

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(tween(256)) + fadeIn(),
        exit = slideOutVertically(tween(256)) + fadeOut(),
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Destinations.bottomNavigationBarItems.forEachIndexed { index, destination ->
                NavigationBarItem(
                    enabled = destination.destinations != null,
                    selected = selectedDestination == index,
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
