package com.salazar.withings.feature.details

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.salazar.withings.data.picture.Picture


suspend fun PagerState.previousPage() {
    animateScrollToPage(currentPage - 1)
}

@Composable
fun PicturePage(
    picture: Picture,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onPauseChange: (Boolean) -> Unit,
) {
    AsyncImage(
        model = picture.imageUrl,
        contentDescription = null,
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(9 / 16f)
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .pointerInput(Unit) {
                val maxWidth = this.size.width
                detectTapGestures(
                    onPress = {
                        val pressStartTime = System.currentTimeMillis()

                        onPauseChange(true)
                        val wasConsumedByOtherGesture = !tryAwaitRelease()
                        onPauseChange(false)
                        if (wasConsumedByOtherGesture) return@detectTapGestures

                        val pressEndTime = System.currentTimeMillis()
                        val totalPressTime = pressEndTime - pressStartTime

                        if (totalPressTime > 200) return@detectTapGestures

                        val isTapOnRightThreeQuarters = (it.x > (maxWidth / 4))
                        if (isTapOnRightThreeQuarters)
                            onNextStep()
                        else
                            onPreviousStep()
                    },
                )
            }
    )
}

@Composable
fun PictureFeedHeader(
    picture: Picture,
    count: Int,
    currentStep: Int,
    onStepFinish: (Boolean) -> Unit,
    pause: Boolean,
) {
    Column {
        ProgressBar(
            steps = count,
            currentStep = currentStep,
            paused = pause,
            onStepFinish = onStepFinish,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(5.dp)
                    .size(32.dp)
                    .clip(CircleShape),
                model = picture.userAvatar,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = picture.user)
        }
    }
}