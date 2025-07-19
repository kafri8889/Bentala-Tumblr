package com.anafthdev.bentalatumblr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.anafthdev.bentalatumblr.ui.app.BentalaApp
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

//        val rootView = findViewById<View>(android.R.id.content).rootView
//        WindowCompat.getInsetsController(window, rootView).isAppearanceLightStatusBars = true
//        WindowCompat.getInsetsController(window, rootView).isAppearanceLightNavigationBars = true

        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isLoggedIn == null && viewModel.isFirstInstall == null
        }

        actionBar?.hide()

        setContent {
            val isLoggedIn = remember(viewModel.isLoggedIn) {
                viewModel.isLoggedIn
            }

            val isFirstInstall = remember(viewModel.isFirstInstall) {
                viewModel.isFirstInstall
            }

            // ---- JANGAN HAPUS KODE INI, KALO DI HAPUS, SEMUA INSETS BAKAL 0 NILAINYA ----
            val insets = WindowInsets.systemBars.asPaddingValues()

            LaunchedEffect(Unit) {
                Timber.i("kon: $insets")
            }
            // ---- JANGAN HAPUS KODE INI, KALO DI HAPUS, SEMUA INSETS BAKAL 0 NILAINYA ----

            if (isLoggedIn != null && isFirstInstall != null) {
                BentalaApp(
                    isFirstInstall = isFirstInstall,
                    isLoggedIn = isLoggedIn
                )
            }
        }
    }
}
