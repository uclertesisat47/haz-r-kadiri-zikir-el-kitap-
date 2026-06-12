package com.kadiri.elkitabi.features.zikir.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.PrimaryGradient
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

@Composable
fun ZikirTamamModal(
    turSayisi: Int,
    zikirAdi: String,
    onDismiss: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val scale by animateFloatAsState(
        targetValue   = if (visible) 1f else 0.5f,
        animationSpec = tween(400),
        label         = "modal_scale"
    )
    val alpha by animateFloatAsState(
        targetValue   = if (visible) 1f else 0f,
        animationSpec = tween(300),
        label         = "modal_alpha"
    )

    Box(
        modifier         = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier            = Modifier
                .scale(scale)
                .alpha(alpha)
                .clip(RoundedCornerShape(24.dp))
                .background(Brush.verticalGradient(PrimaryGradient))
                .padding(Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("الحمد لله", style = MaterialTheme.typography.displaySmall, color = GoldAccent)
            Spacer(Modifier.height(Spacing.md))
            Text(
                text      = "Mâşâallah!\n$turSayisi. tur tamamlandı",
                style     = MaterialTheme.typography.headlineSmall,
                color     = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text  = zikirAdi,
                style = MaterialTheme.typography.titleMedium,
                color = GoldAccent.copy(alpha = 0.8f)
            )
            Spacer(Modifier.height(Spacing.lg))
            Text(
                text  = "✦  ✦  ✦",
                style = MaterialTheme.typography.bodyLarge,
                color = GoldAccent
            )
        }
    }
}
