package com.salazar.withings.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.withings.data.picture.Picture
import com.salazar.withings.data.picture.repository.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DetailsUiState(
    val name: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val searchInput: String = "",
    val pictures: List<Picture>? = null,
    val selectedPictures: Set<Picture> = emptySet(),
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(DetailsUiState(isLoading = true))
    private var searchJob: Job? = null

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init {
    }

    fun onDetailsInputChanged(searchInput: String) {
        viewModelState.update {
            it.copy(searchInput = searchInput, isLoading = true)
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            queryUsers(query = searchInput)
        }
    }

    private fun queryUsers(
        query: String = uiState.value.searchInput.lowercase(),
    ) {
        viewModelScope.launch {
            val result = pictureRepository.searchPictures(query = query)
            result.onSuccess {
                updatePictures(it)
            }
            result.onFailure {
                // TODO
            }
        }
    }

    fun updateIsLoading(isLoading: Boolean) {
        viewModelState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun updatePictures(pictures: List<Picture>?) {
        viewModelState.update {
            it.copy(pictures = pictures)
        }
    }
}
