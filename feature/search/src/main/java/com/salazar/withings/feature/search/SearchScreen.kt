package com.salazar.withings.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.salazar.withings.data.picture.Picture

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onSearchInputChanged: (String) -> Unit,
    onPictureClick: (Picture) -> Unit,
) {
    var active by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
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
                    pictures = uiState.pictures,
                    onPictureClick = onPictureClick,
                )
            }
        },
    ) {
        it
    }
}

@Composable
fun PictureList(
    pictures: List<Picture>?,
    onPictureClick: (Picture) -> Unit,
) {
    if (pictures == null)
        return

    LazyColumn {
        items(pictures) { picture ->
            PictureItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                picture = picture,
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
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable { onClick() },
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .height(64.dp)
            ,
            model = picture.imageUrl,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = picture.user,
        )
    }
}