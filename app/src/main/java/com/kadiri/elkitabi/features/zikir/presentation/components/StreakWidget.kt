package com.kadiri.elkitabi.features.zikir.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.WarmAmber

@Composable
fun StreakWidget(
    streak: Int,
    modifier: Modifier = Modifier
) {
    IslamicCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector        = Icons.Filled.LocalFireDepartment,
                    contentDescription = "Streak",
                    tint               = WarmAmber,
                    modifier           = Modifier.size(32.dp)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("Ardışık Gün", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$streak gün", style = MaterialTheme.typography.titleLarge, color = WarmAmber)
                }
            }
            Text(
                text  = if (streak >= 7) "🏆" else if (streak >= 3) "⭐" else "🌱",
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}
