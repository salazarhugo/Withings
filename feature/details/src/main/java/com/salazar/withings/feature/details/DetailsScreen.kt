package com.salazar.withings.feature.details

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.salazar.withings.data.picture.Picture
import com.salazar.withings.data.picture.SearchUiState
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    uiState: SearchUiState,
    navigateBack: () -> Unit,
) {
    val selectedPictures = uiState.selectedPictures

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Details",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) {
        PictureList(
            modifier = Modifier.padding(it),
            pictures = uiState.selectedPictures.toList(),
        )
    }
}

@Composable
fun PictureList(
    modifier: Modifier = Modifier,
    pictures: List<Picture>?,
) {
    if (pictures == null)
        return

    val scope = rememberCoroutineScope()
    val stepperState = rememberPagerState()
    var paused by remember { mutableStateOf(false) }
    val currentStep = stepperState.currentPage

    Scaffold(
        modifier = modifier,
        topBar = {
            PictureFeedHeader(
                picture = pictures[currentStep],
                count = pictures.size,
                onStepFinish = { last ->
                    if (!last)
                        scope.launch {
                            stepperState.animateScrollToPage(stepperState.currentPage + 1)
                        }
                },
                currentStep = currentStep,
                pause = paused,
            )
        },
        containerColor = Color.Black,
    ) {
        HorizontalPager(
            modifier = Modifier.padding(it),
            pageCount = pictures.size,
            state = stepperState,
            verticalAlignment = Alignment.Top,
        ) { step ->
            val picture = pictures[step]
            PicturePage(
                picture = picture,
                onPauseChange = {
                    paused = it
                },
                onNextStep = {
                    if (step != pictures.size - 1)
                        scope.launch {
                            stepperState.animateScrollToPage(stepperState.currentPage + 1)
                        }
                },
                onPreviousStep = {
                    if (step != 0)
                        scope.launch {
                            stepperState.previousPage()
                        }
                },
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
    Row(
        modifier = modifier
            .clickable { onClick() },
    ) {
        if (isSelected)
            Icon(Icons.Default.Check, contentDescription = null)

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