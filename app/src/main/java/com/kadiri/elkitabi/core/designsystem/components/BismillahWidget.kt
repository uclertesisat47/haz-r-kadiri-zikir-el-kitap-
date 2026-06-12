package com.kadiri.elkitabi.core.designsystem.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kadiri.elkitabi.core.designsystem.theme.ArabicTextLarge
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

@Composable
fun BismillahWidget(
    modifier: Modifier = Modifier,
    animate: Boolean = false,
    color: Color = GoldAccent,
    showTranslation: Boolean = false
) {
    val alpha by if (animate) {
        rememberInfiniteTransition(label = "bismillah").animateFloat(
            initialValue = 0.7f,
            targetValue  = 1.0f,
            animationSpec = infiniteRepeatable(
                animation  = tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bismillah_alpha"
        )
    } else {
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
    }

    Column(
        modifier           = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Text(
            text      = "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيمِ",
            style     = ArabicTextLarge.copy(fontSize = 24.sp, color = color),
            modifier  = Modifier.alpha(alpha),
            textAlign = TextAlign.Center
        )
        if (showTranslation) {
            Text(
                text  = "Rahman ve Rahim olan Allah'ın adıyla",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
