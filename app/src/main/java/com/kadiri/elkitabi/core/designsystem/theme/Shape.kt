package com.kadiri.elkitabi.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

val KadiriShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small      = RoundedCornerShape(8.dp),
    medium     = RoundedCornerShape(16.dp),
    large      = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

val IslamicCardShape = RoundedCornerShape(
    topStart = 24.dp, topEnd = 24.dp,
    bottomStart = 16.dp, bottomEnd = 16.dp
)

val StadiumShape = RoundedCornerShape(percent = 50)

// Pointed arch — cami kemeri formu
object ArchShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            val w = size.width
            val h = size.height
            val archHeight = h * 0.35f
            moveTo(0f, h)
            lineTo(0f, archHeight)
            cubicTo(0f, 0f, w / 2f, 0f, w / 2f, 0f)
            cubicTo(w / 2f, 0f, w, 0f, w, archHeight)
            lineTo(w, h)
            close()
        }
        return Outline.Generic(path)
    }
}

// 8 köşeli İslami yıldız formu
object OctagonShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            val cx = size.width / 2f
            val cy = size.height / 2f
            val r = minOf(cx, cy)
            val cut = r * 0.414f
            moveTo(cx - r + cut, cy - r)
            lineTo(cx + r - cut, cy - r)
            lineTo(cx + r, cy - r + cut)
            lineTo(cx + r, cy + r - cut)
            lineTo(cx + r - cut, cy + r)
            lineTo(cx - r + cut, cy + r)
            lineTo(cx - r, cy + r - cut)
            lineTo(cx - r, cy - r + cut)
            close()
        }
        return Outline.Generic(path)
    }
}
