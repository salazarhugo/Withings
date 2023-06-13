package com.salazar.withings.feature.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    steps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    paused: Boolean = false,
    onStepFinish: (last: Boolean) -> Unit
) {
    val percent = remember { Animatable(0f) }

    LaunchedEffect(paused) {
        if (paused)
            percent.stop()
        else {
            percent.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = (10000 * (1f - percent.value)).toInt(),
                    easing = LinearEasing
                )
            )
            onStepFinish(currentStep == steps - 1)
        }
    }

    LaunchedEffect(currentStep) {
        percent.snapTo(0f)
        percent.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = (10000 * (1f - percent.value)).toInt(),
                easing = LinearEasing
            )
        )
        onStepFinish(currentStep == steps - 1)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        for (index in 0 until steps) {
            Row(
                modifier = Modifier
                    .height(2.dp)
                    .clip(RoundedCornerShape(50, 50, 50, 50))
                    .weight(1f)
                    .background(Color.White.copy(alpha = 0.4f))
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxHeight().let {
                            when (index) {
                                currentStep -> it.fillMaxWidth(percent.value)
                                in 0..currentStep -> it.fillMaxWidth(1f)
                                else -> it
                            }
                        },
                ) {}
            }
            if (index != steps) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}