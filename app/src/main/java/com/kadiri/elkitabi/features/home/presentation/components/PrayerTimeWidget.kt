package com.kadiri.elkitabi.features.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen

@Composable
fun PrayerTimeWidget(
    nextPrayer: String,
    nextPrayerTime: String,
    remainingTime: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IslamicCard(
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(androidx.compose.ui.unit.Dp(12f)),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.AccessTime, contentDescription = null, tint = GoldAccent)
                Column {
                    Text(
                        text  = if (nextPrayer.isBlank()) "Sıradaki Namaz" else nextPrayer,
                        style = MaterialTheme.typography.titleSmall,
                        color = IslamicGreen
                    )
                    Text(
                        text  = if (nextPrayerTime.isBlank()) "—" else nextPrayerTime,
                        style = MaterialTheme.typography.headlineSmall,
                        color = GoldAccent
                    )
                    if (remainingTime.isNotBlank()) {
                        Text(
                            text  = "Kalan: $remainingTime",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
