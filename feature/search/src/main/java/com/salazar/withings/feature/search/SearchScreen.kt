package com.salazar.withings.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.salazar.withings.data.picture.Picture
import com.salazar.withings.data.picture.SearchUiState

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onSearchInputChanged: (String) -> Unit,
    onPictureClick: (Picture) -> Unit,
    onConfirm: () -> Unit,
    navigateBack: () -> Unit,
) {
    var active by rememberSaveable { mutableStateOf(false) }
    val selectedPictures = uiState.selectedPictures

    Scaffold(
        topBar = {
            if (selectedPictures.isNotEmpty())
                TopAppBar(
                    title = {
                            Text(
                                text = selectedPictures.size.toString(),
                            )
                    },
                    navigationIcon = {
                        IconButton(onClick = navigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {
                        Button(
                            onClick = onConfirm,
                            enabled = selectedPictures.size >= 2
                        ) {
                            Text(text = "Details")
                        }
                    }
                )
            else
                SearchBar(
                    modifier = Modifier
                        .statusBarsPadding(),
                    query = uiState.searchInput,
                    onQueryChange = onSearchInputChanged,
                    onSearch = {},
                    active = active,
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    placeholder = {
                        Text(text = "Search images")
                    },
                    onActiveChange = {
                         active = it
                    },
                ) {
                    PictureList(
                        selectedPictures = uiState.selectedPictures,
                        pictures = uiState.pictures,
                        onPictureClick = onPictureClick,
                    )
                }
        },
    ) {
        PictureList(
            modifier = Modifier.padding(it),
            selectedPictures = uiState.selectedPictures,
            pictures = uiState.pictures,
            onPictureClick = onPictureClick,
        )
    }
}

@Composable
fun PictureList(
    modifier: Modifier = Modifier,
    selectedPictures: Set<Picture>,
    pictures: List<Picture>?,
    onPictureClick: (Picture) -> Unit,
) {
    if (pictures == null)
        return

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = modifier,
    ) {
        items(pictures) { picture ->
            PictureItem(
                modifier = Modifier
                    .padding(4.dp),
                picture = picture,
                isSelected = selectedPictures.contains(picture),
                onClick = {
                    onPictureClick(picture)
                }
            )
        }
    }
}

@Composable
fun PictureItem(
    modifier: Modifier = Modifier,
    picture: Picture,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .height(64.dp)
                .clickable { onClick() }
                .let {
                     if(isSelected)
                         return@let it.alpha(0.6f)
                    it
                }
            ,
            model = picture.imageUrl,
            contentDescription = null,
        )
        if (isSelected)
            Icon(Icons.Default.Check, contentDescription = null)
    }
}