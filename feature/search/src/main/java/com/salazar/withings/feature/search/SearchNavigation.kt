package com.salazar.withings.feature.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink

const val searchNavigationRoute = "search_route"
private const val DEEP_LINK_URI_PATTERN =
    "https://searcharty.app/search"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchNavigationRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    navigateToPictureDetails: (String) -> Unit,
) {
    composable(
        route = searchNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        SearchRoute(
            navigateToPictureDetails = navigateToPictureDetails,
        )
    }
}
