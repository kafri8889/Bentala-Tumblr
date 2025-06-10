package com.anafthdev.bentalatumblr.ui.app

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anafthdev.bentalatumblr.data.Destinations
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme
import com.anafthdev.bentalatumblr.ui.home.HomeScreen

@Composable
fun BentalaApp() {

    val navController = rememberNavController()

    BentalaTumblrTheme {
        NavHost(
            navController = navController,
            startDestination = Destinations.Home
        ) {
            composable<Destinations.Home> { backEntry ->
                HomeScreen(
                    viewModel = hiltViewModel(backEntry)
                )
            }
        }
    }

}
