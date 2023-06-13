package com.salazar.withings.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.salazar.withings.WithingsAppState
import com.salazar.withings.data.picture.SearchViewModel
import com.salazar.withings.feature.details.detailsScreen
import com.salazar.withings.feature.details.navigateToDetails
import com.salazar.withings.feature.search.searchNavigationRoute
import com.salazar.withings.feature.search.searchScreen


@Composable
fun NavGraph(
    appState: WithingsAppState,
) {
    val startDestination = searchNavigationRoute
    val navController = appState.navController
    val sharedViewModel = hiltViewModel<SearchViewModel>()

    NavHost(
        modifier = Modifier.padding(),
        route = "root",
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        searchScreen(
            viewModel = sharedViewModel,
            navigateToPictureDetails = {
                navController.navigateToDetails()
            },
        )
        detailsScreen(
            viewModel = sharedViewModel,
            navigateBack = appState::navigateBack,
        )
    }
}