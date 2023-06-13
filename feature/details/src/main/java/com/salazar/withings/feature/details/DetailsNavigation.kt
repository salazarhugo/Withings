package com.salazar.withings.feature.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.salazar.withings.data.picture.SearchViewModel

//const val PICTURES = "pictures"
const val detailsNavigationRoute = "details_route"
private const val DEEP_LINK_URI_PATTERN =
    "https://withings.com/details"

fun NavController.navigateToDetails(navOptions: NavOptions? = null) {
    this.navigate(detailsNavigationRoute, navOptions)
}

fun NavGraphBuilder.detailsScreen(
    viewModel: SearchViewModel,
    navigateBack: () -> Unit,
) {
    composable(
        route = detailsNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        DetailsRoute(
            viewModel = viewModel,
            navigateBack = navigateBack,
        )
    }
}
