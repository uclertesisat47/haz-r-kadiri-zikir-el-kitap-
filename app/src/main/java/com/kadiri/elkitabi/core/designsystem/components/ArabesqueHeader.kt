package com.kadiri.elkitabi.core.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent

@Composable
fun ArabesqueHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    patternColor: Color = ArabesqueGold.copy(alpha = 0.5f),
    headerHeight: Dp = 72.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight),
        contentAlignment = Alignment.Center
    ) {
        ArabesquePatternCanvas(
            modifier = Modifier.fillMaxWidth().height(headerHeight),
            color = patternColor
        )
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ArabesquePatternCanvas(
    modifier: Modifier = Modifier,
    color: Color = ArabesqueGold.copy(alpha = 0.3f)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = Stroke(width = 1.5f, cap = StrokeCap.Round)

        // Sol arabesk motifi
        for (i in 0..3) {
            val cx = 40f + i * 20f
            val cy = h / 2f
            val r = 8f - i * 1.5f
            drawCircle(color = color, radius = r, center = Offset(cx, cy), style = stroke)
        }

        // Sol yatay çizgi
        val path1 = Path().apply {
            moveTo(0f, h / 2f)
            lineTo(w * 0.35f, h / 2f)
        }
        drawPath(path1, color, style = stroke)

        // Sağ yatay çizgi
        val path2 = Path().apply {
            moveTo(w * 0.65f, h / 2f)
            lineTo(w, h / 2f)
        }
        drawPath(path2, color, style = stroke)

        // Merkez diamond
        val cx = w / 2f
        val cy = h / 2f
        val dm = 10f
        val diamond = Path().apply {
            moveTo(cx, cy - dm)
            lineTo(cx + dm, cy)
            lineTo(cx, cy + dm)
            lineTo(cx - dm, cy)
            close()
        }
        drawPath(diamond, color, style = stroke)

        // Sağ arabesk motifi
        for (i in 0..3) {
            val x = w - 40f - i * 20f
            val cy2 = h / 2f
            val r = 8f - i * 1.5f
            drawCircle(color = color, radius = r, center = Offset(x, cy2), style = stroke)
        }
    }
}
