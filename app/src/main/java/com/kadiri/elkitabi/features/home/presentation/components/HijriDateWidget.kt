package com.kadiri.elkitabi.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent

@Composable
fun HijriDateWidget(
    hijriDate: String,
    miladi: String,
    modifier: Modifier = Modifier
) {
    IslamicCard(modifier = modifier) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text  = "☽",
                    style = MaterialTheme.typography.headlineMedium,
                    color = ArabesqueGold
                )
                Text(
                    text  = hijriDate.ifBlank { "Hicri Tarih" },
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldAccent
                )
                Text(
                    text  = miladi.ifBlank { "Miladi Tarih" },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text  = "بِسْمِ اللهِ",
                style = MaterialTheme.typography.titleLarge,
                color = ArabesqueGold.copy(alpha = 0.6f)
            )
        }
    }
}
