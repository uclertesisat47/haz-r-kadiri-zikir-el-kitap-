package com.kadiri.elkitabi.core.designsystem.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.kadiri.elkitabi.core.designsystem.theme.PrimaryDark
import com.kadiri.elkitabi.core.designsystem.theme.DURATION_MEDIUM

@Composable
fun PulseAnimation(
    modifier: Modifier = Modifier,
    pulsing: Boolean = true,
    minScale: Float = 0.97f,
    maxScale: Float = 1.03f,
    durationMillis: Int = DURATION_MEDIUM * 2,
    content: @Composable () -> Unit
) {
    val scale by if (pulsing) {
        rememberInfiniteTransition(label = "pulse").animateFloat(
            initialValue  = minScale,
            targetValue   = maxScale,
            animationSpec = infiniteRepeatable(
                animation  = tween(durationMillis),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse_scale"
        )
    } else {
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
    }

    Box(
        modifier          = modifier.scale(scale),
        contentAlignment  = Alignment.Center
    ) {
        content()
    }
}
