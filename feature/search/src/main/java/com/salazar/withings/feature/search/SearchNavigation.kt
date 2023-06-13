package com.salazar.withings.feature.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.salazar.withings.data.picture.SearchViewModel

const val searchNavigationRoute = "search_route"
private const val DEEP_LINK_URI_PATTERN =
    "https://withings.com/search"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchNavigationRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    viewModel: SearchViewModel,
    navigateToPictureDetails: (String) -> Unit,
) {
    composable(
        route = searchNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        SearchRoute(
            viewModel = viewModel,
            navigateToPictureDetails = navigateToPictureDetails,
        )
    }
}
