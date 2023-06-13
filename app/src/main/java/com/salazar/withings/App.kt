package com.salazar.withings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.salazar.withings.navigation.NavGraph
import com.salazar.withings.ui.theme.WithingsTheme


@Composable
fun WithingsApp(
    appState: WithingsAppState = rememberWithingsAppState()
) {
    SetStatusBars(darkTheme = isSystemInDarkTheme())

    WithingsTheme() {
        NavGraph(
            appState = appState,
        )
    }
}

@Composable
fun SetStatusBars(
    darkTheme: Boolean,
) {
    val systemUiController = rememberSystemUiController()
    val darkIcons = !darkTheme

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = darkIcons,
        )
    }
}
