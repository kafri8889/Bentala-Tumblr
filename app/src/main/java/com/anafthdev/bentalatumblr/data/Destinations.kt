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
    data object Statistic: Destinations()

    @Serializable
    data object Mission: Destinations()

    @Serializable
    data object Profile: Destinations()

    @Serializable
    data object FindTumblr: Destinations()

    @Serializable
    data object Reminder: Destinations()

    @Serializable
    data object Marketplace: Destinations()

    object Auth {

        @Serializable
        data object Onboarding: Destinations()

        @Serializable
        data object Login: Destinations()

        @Serializable
        data object Register: Destinations()

    }

    companion object {
        val bottomNavItemHome = BottomNavigationBarItem(
            label = R.string.nav_bar_dashboard,
            icon = R.drawable.ic_home_outline,
            destinations = Home
        )

        val bottomNavItemAnalysis = BottomNavigationBarItem(
            label = R.string.nav_bar_analysis,
            icon = R.drawable.ic_analysis_outline,
            destinations = Statistic
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
