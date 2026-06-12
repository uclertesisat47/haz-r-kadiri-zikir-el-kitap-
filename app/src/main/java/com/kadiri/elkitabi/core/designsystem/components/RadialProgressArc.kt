package com.kadiri.elkitabi.core.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.PrimaryDark
import com.kadiri.elkitabi.core.designsystem.theme.SpringZikir

@Composable
fun RadialProgressArc(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = Color.White.copy(alpha = 0.12f),
    progressColor: Color = GoldAccent,
    strokeWidth: Float = 14f,
    startAngle: Float = -90f,
    showGlow: Boolean = true
) {
    val animatedProgress by animateFloatAsState(
        targetValue   = progress.coerceIn(0f, 1f),
        animationSpec = SpringZikir,
        label         = "radial_progress"
    )

    Canvas(modifier = modifier) {
        val diameter = minOf(size.width, size.height) - strokeWidth
        val topLeft  = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
        val arcSize  = Size(diameter, diameter)

        // Track
        drawArc(
            color      = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter  = false,
            topLeft    = topLeft,
            size       = arcSize,
            style      = Stroke(strokeWidth, cap = StrokeCap.Round)
        )

        // Progress arc
        if (animatedProgress > 0f) {
            drawArc(
                color      = progressColor,
                startAngle = startAngle,
                sweepAngle = 360f * animatedProgress,
                useCenter  = false,
                topLeft    = topLeft,
                size       = arcSize,
                style      = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
}
