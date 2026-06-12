package com.kadiri.elkitabi.core.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

enum class IslamicPattern { STAR_8, STAR_10, STAR_6 }

@Composable
fun GeometricIslamicBackground(
    modifier: Modifier = Modifier,
    patternColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
    patternType: IslamicPattern = IslamicPattern.STAR_8
) {
    Canvas(modifier = modifier) {
        val cellSize = 80f
        val cols = (size.width / cellSize).toInt() + 2
        val rows = (size.height / cellSize).toInt() + 2

        for (row in 0..rows) {
            for (col in 0..cols) {
                val cx = col * cellSize
                val cy = row * cellSize
                when (patternType) {
                    IslamicPattern.STAR_8  -> drawIslamicStar8(cx, cy, cellSize * 0.35f, patternColor)
                    IslamicPattern.STAR_10 -> drawIslamicStar10(cx, cy, cellSize * 0.35f, patternColor)
                    IslamicPattern.STAR_6  -> drawIslamicStar6(cx, cy, cellSize * 0.35f, patternColor)
                }
            }
        }
    }
}

private fun DrawScope.drawIslamicStar8(cx: Float, cy: Float, r: Float, color: Color) {
    val points = 8
    val innerR = r * 0.38f
    val path = Path()
    for (i in 0 until points * 2) {
        val angle = (i * PI / points - PI / 2).toFloat()
        val radius = if (i % 2 == 0) r else innerR
        val x = cx + radius * cos(angle)
        val y = cy + radius * sin(angle)
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color)
}

private fun DrawScope.drawIslamicStar10(cx: Float, cy: Float, r: Float, color: Color) {
    val points = 10
    val innerR = r * 0.40f
    val path = Path()
    for (i in 0 until points * 2) {
        val angle = (i * PI / points - PI / 2).toFloat()
        val radius = if (i % 2 == 0) r else innerR
        val x = cx + radius * cos(angle)
        val y = cy + radius * sin(angle)
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color)
}

private fun DrawScope.drawIslamicStar6(cx: Float, cy: Float, r: Float, color: Color) {
    val points = 6
    val innerR = r * 0.45f
    val path = Path()
    for (i in 0 until points * 2) {
        val angle = (i * PI / points - PI / 2).toFloat()
        val radius = if (i % 2 == 0) r else innerR
        val x = cx + radius * cos(angle)
        val y = cy + radius * sin(angle)
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color)
}
