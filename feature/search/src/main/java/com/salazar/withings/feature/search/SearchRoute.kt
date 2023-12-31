package com.salazar.withings.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salazar.withings.data.picture.SearchViewModel

@Composable
fun SearchRoute(
    viewModel: SearchViewModel,
    navigateToPictureDetails: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        onSearchInputChanged = viewModel::onSearchInputChanged,
        onPictureClick = {
             viewModel.onPictureClick(it)
        },
        onConfirm = {
            navigateToPictureDetails("")
        },
        navigateBack = {
            viewModel.clearSelection()
        }
    )
}