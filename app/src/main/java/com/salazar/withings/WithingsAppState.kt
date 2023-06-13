package com.salazar.withings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun rememberWithingsAppState(
    navController: NavHostController = rememberNavController(),
) = remember(
    navController,
) {
    WithingsAppState(
        navController,
    )
}

class WithingsAppState(
    val navController: NavHostController,
) {
    fun navigateBack() {
        navController.popBackStack()
    }
}
