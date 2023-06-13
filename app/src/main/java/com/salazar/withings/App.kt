package com.salazar.withings

import androidx.compose.runtime.Composable
import com.salazar.withings.navigation.NavGraph
import com.salazar.withings.ui.theme.WithingsTheme


@Composable
fun WithingsApp(
    appState: WithingsAppState = rememberWithingsAppState()
) {
    WithingsTheme() {
        NavGraph(
            appState = appState,
        )
    }
}