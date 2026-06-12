package com.kadiri.elkitabi.features.zikir.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ArabesqueRing(
    modifier: Modifier = Modifier,
    rotating: Boolean = true,
    color: Color = ArabesqueGold.copy(alpha = 0.35f)
) {
    val rotation by if (rotating) {
        rememberInfiniteTransition(label = "arabesk_ring").animateFloat(
            initialValue  = 0f,
            targetValue   = 360f,
            animationSpec = infiniteRepeatable(
                animation  = tween(30_000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "ring_rotation"
        )
    } else {
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    }

    Canvas(modifier = modifier) {
        val cx   = size.width / 2f
        val cy   = size.height / 2f
        val r    = minOf(cx, cy) - 10f

        rotate(rotation, Offset(cx, cy)) {
            // 8 arabesk yaprak
            for (i in 0 until 16) {
                val angle = i * PI / 8
                val x     = cx + r * cos(angle).toFloat()
                val y     = cy + r * sin(angle).toFloat()
                if (i % 2 == 0) {
                    drawIslamicFlower(cx, cy, x, y, r * 0.12f, color)
                } else {
                    drawCircle(color = color, radius = 3f, center = Offset(x, y))
                }
            }
            // Dış halka
            drawCircle(color = color.copy(alpha = color.alpha * 0.5f), radius = r, center = Offset(cx, cy),
                style = androidx.compose.ui.graphics.drawscope.Stroke(2f))
        }
    }
}

private fun DrawScope.drawIslamicFlower(
    cx: Float, cy: Float,
    px: Float, py: Float,
    size: Float, color: Color
) {
    val dx    = px - cx
    val dy    = py - cy
    val angle = kotlin.math.atan2(dy, dx)
    val path  = Path().apply {
        moveTo(px, py)
        cubicTo(
            px + size * cos(angle + PI / 3).toFloat(),
            py + size * sin(angle + PI / 3).toFloat(),
            px + size * cos(angle - PI / 3).toFloat(),
            py + size * sin(angle - PI / 3).toFloat(),
            px, py
        )
    }
    drawPath(path, color)
}
