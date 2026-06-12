package com.kadiri.elkitabi.core.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold

@Composable
fun IslamicDivider(
    modifier: Modifier = Modifier,
    color: Color = ArabesqueGold.copy(alpha = 0.4f)
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
    ) {
        val w = size.width
        val cy = size.height / 2f
        val stroke = Stroke(width = 1.5f, cap = StrokeCap.Round)

        // Sol çizgi
        drawLine(color, Offset(0f, cy), Offset(w * 0.38f, cy), 1.5f)

        // Merkez tezhip motifi (3 baklava)
        val cx = w / 2f
        for (i in -1..1) {
            val x = cx + i * 14f
            val d = 6f
            val path = Path().apply {
                moveTo(x, cy - d)
                lineTo(x + d * 0.6f, cy)
                lineTo(x, cy + d)
                lineTo(x - d * 0.6f, cy)
                close()
            }
            drawPath(path, color, style = stroke)
        }

        // Sağ çizgi
        drawLine(color, Offset(w * 0.62f, cy), Offset(w, cy), 1.5f)
    }
}
