package com.kadiri.elkitabi.features.zikir.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen

@Composable
fun ZikirDaireUI(
    progress: Float,
    onTap: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 260.dp,
    enabled: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue   = if (pressed) 0.95f else 1.0f,
        animationSpec = tween(100),
        label         = "press_scale"
    )

    val clampedProgress = progress.coerceIn(0f, 1f)

    Box(
        modifier         = modifier
            .size(size)
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                    },
                    onTap = { onTap() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokeWidth = this.size.width * 0.05f
            val inset       = strokeWidth / 2f
            val arcSize     = Size(
                this.size.width  - inset * 2f,
                this.size.height - inset * 2f
            )
            val topLeft     = Offset(inset, inset)

            // Arka plan halkası
            drawArc(
                color       = Color.White.copy(alpha = 0.07f),
                startAngle  = -90f,
                sweepAngle  = 360f,
                useCenter   = false,
                topLeft     = topLeft,
                size        = arcSize,
                style       = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // İlerleme yayı
            if (clampedProgress > 0f) {
                drawArc(
                    brush      = Brush.sweepGradient(
                        colors = listOf(IslamicGreen, GoldAccent, ArabesqueGold),
                        center = Offset(this.size.width / 2f, this.size.height / 2f)
                    ),
                    startAngle = -90f,
                    sweepAngle = 360f * clampedProgress,
                    useCenter  = false,
                    topLeft    = topLeft,
                    size       = arcSize,
                    style      = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }

            // İç daire vurgu
            drawCircle(
                color  = GoldAccent.copy(alpha = if (pressed) 0.18f else 0.08f),
                radius = (this.size.minDimension / 2f) - strokeWidth * 1.5f,
                center = Offset(this.size.width / 2f, this.size.height / 2f)
            )
        }
        content()
    }
}
