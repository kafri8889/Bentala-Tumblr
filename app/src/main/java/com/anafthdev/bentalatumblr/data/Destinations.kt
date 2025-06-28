package com.anafthdev.bentalatumblr.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.anafthdev.bentalatumblr.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations {

    @Serializable
    data object Home: Destinations()

    @Serializable
    data object Analysis: Destinations()

    @Serializable
    data object Mission: Destinations()

    @Serializable
    data object Profile: Destinations()

    /**
     * Drink screen, user can add a new drink history
     */
    @Serializable
    data object AddRecord: Destinations()

    companion object {
        val bottomNavItemHome = BottomNavigationBarItem(
            label = R.string.nav_bar_dashboard,
            icon = R.drawable.ic_home_outline,
            destinations = Home
        )

        val bottomNavItemAnalysis = BottomNavigationBarItem(
            label = R.string.nav_bar_analysis,
            icon = R.drawable.ic_analysis_outline,
            destinations = Analysis
        )

        val bottomNavItemMission = BottomNavigationBarItem(
            label = R.string.nav_bar_mission,
            icon = R.drawable.ic_mission_outline,
            destinations = Mission
        )

        val bottomNavItemProfile = BottomNavigationBarItem(
            label = R.string.nav_bar_profile,
            icon = R.drawable.ic_profile_outline,
            destinations = Profile
        )

        val bottomNavigationBarItems = listOf(
            bottomNavItemHome,
            bottomNavItemAnalysis,
            bottomNavItemMission,
            bottomNavItemProfile
        )
    }

}

data class BottomNavigationBarItem(
    @StringRes val label: Int? = null,
    @DrawableRes val icon: Int? = null,
    val destinations: Destinations? = null
)
