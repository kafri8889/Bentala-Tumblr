package com.anafthdev.bentalatumblr.foundation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class BentalaColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val lightBlueTextColor: Color,
    val blueTextColor: Color,
    val lightTextColor: Color,
    val textColor: Color,
    val background: Color,
    val onBackground: Color,
)

fun lightBentalaColorScheme(
    primary: Color = Color(0xFF5DCCFC),
    onPrimary: Color = Color(0xFFFFFFFF),
    secondary: Color = Color(0xFF0F80FD),
    onSecondary: Color = Color(0xFFFFFFFF),
    lightBlueTextColor: Color = Color(0xFF9BE0FF),
    blueTextColor: Color = Color(0xFF5686F5),
    lightTextColor: Color = Color(0xFF625D5D),
    textColor: Color = Color(0xFF000000),
    background: Color = Color(0xFFF4F8FB),
    onBackground: Color = textColor,
) = BentalaColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    onSecondary = onSecondary,
    lightBlueTextColor = lightBlueTextColor,
    blueTextColor = blueTextColor,
    lightTextColor = lightTextColor,
    textColor = textColor,
    background = background,
    onBackground = onBackground,
)

val LocalBentalaColorScheme = staticCompositionLocalOf { lightBentalaColorScheme() }

object BentalaTheme {

    val colorScheme: BentalaColorScheme
        @Composable @ReadOnlyComposable get() = LocalBentalaColorScheme.current

}
