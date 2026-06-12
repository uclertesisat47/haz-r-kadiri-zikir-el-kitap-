package com.kadiri.elkitabi.features.kible.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KibleScreen(onBack: () -> Unit) {
    // Kıble yönü (İstanbul için yaklaşık 155° güneybatı)
    val kibleAcisi by animateFloatAsState(targetValue = 155f, animationSpec = tween(1000), label = "kible")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kıble Yönü") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } }
            )
        }
    ) { padding ->
        Column(
            modifier            = Modifier.fillMaxSize().padding(padding).padding(Spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(Spacing.xl))
            Text("Kâbe'ye Yön", style = MaterialTheme.typography.headlineSmall)
            Text("İstanbul, Türkiye", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(Spacing.xl))

            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(280.dp)) {
                // Pusula çemberi
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawKompasArkaPlan(this)
                }
                // Kıble oku
                Icon(
                    imageVector        = Icons.Filled.Explore,
                    contentDescription = "Kıble",
                    tint               = GoldAccent,
                    modifier           = Modifier
                        .size(80.dp)
                        .rotate(kibleAcisi)
                )
            }

            Spacer(Modifier.height(Spacing.xl))
            Text(
                text      = "☾  Kâbe-i Şerif'e olan mesafe\n~3.120 km",
                style     = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color     = ArabesqueGold
            )
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text      = "Bu ekran yaklaşık yön göstermektedir.\nKompas izni gereken cihazlarda daha hassas çalışır.",
                style     = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color     = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun drawKompasArkaPlan(scope: DrawScope) = with(scope) {
    val cx   = size.width / 2f
    val cy   = size.height / 2f
    val r    = minOf(cx, cy) - 10f
    val dirs = listOf("K" to 0f, "D" to 90f, "G" to 180f, "B" to 270f)

    drawCircle(color = Color.White.copy(alpha = 0.06f), radius = r, center = Offset(cx, cy),
        style = androidx.compose.ui.graphics.drawscope.Stroke(2f))

    for (deg in 0 until 360 step 10) {
        val rad  = deg * PI / 180f
        val inner = if (deg % 90 == 0) r * 0.82f else r * 0.88f
        val outer = r * 0.98f
        drawLine(
            color  = if (deg % 90 == 0) IslamicGreen else Color.White.copy(alpha = 0.3f),
            start  = Offset(cx + inner * cos(rad).toFloat(), cy + inner * sin(rad - PI / 2).toFloat()),
            end    = Offset(cx + outer * cos(rad).toFloat(), cy + outer * sin(rad - PI / 2).toFloat()),
            strokeWidth = if (deg % 90 == 0) 3f else 1f
        )
    }
}
