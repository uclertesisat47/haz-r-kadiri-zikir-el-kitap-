package com.kadiri.elkitabi.features.zikir.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.zikir.domain.model.GunlukZikir

@Composable
fun IstatistikGrafikler(
    haftalikData: List<GunlukZikir>,
    aylikData: List<GunlukZikir>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        IslamicCard(modifier = Modifier.fillMaxWidth()) {
            Text("Haftalık Zikir", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(Spacing.sm))
            BarChart(data = haftalikData, color = GoldAccent)
        }
        Spacer(Modifier.height(Spacing.md))
        IslamicCard(modifier = Modifier.fillMaxWidth()) {
            Text("Aylık Zikir", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(Spacing.sm))
            BarChart(data = aylikData, color = IslamicGreen)
        }
    }
}

@Composable
private fun BarChart(
    data: List<GunlukZikir>,
    color: Color,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return
    val maxVal = data.maxOf { it.sayi }.coerceAtLeast(1)

    Column(modifier = modifier.fillMaxWidth()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            val barWidth   = (size.width - 8f * (data.size - 1)) / data.size
            val maxHeight  = size.height * 0.9f

            data.forEachIndexed { i, item ->
                val barHeight = (item.sayi.toFloat() / maxVal) * maxHeight
                val left      = i * (barWidth + 8f)
                val top       = size.height - barHeight

                drawRoundRect(
                    color        = color.copy(alpha = 0.15f),
                    topLeft      = Offset(left, 0f),
                    size         = Size(barWidth, size.height),
                    cornerRadius = CornerRadius(4f)
                )
                if (barHeight > 0f) {
                    drawRoundRect(
                        color        = color,
                        topLeft      = Offset(left, top),
                        size         = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(4f)
                    )
                }
            }
        }
        Spacer(Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            data.forEach { item ->
                Text(
                    text     = item.gun,
                    style    = MaterialTheme.typography.labelSmall,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
