package com.kadiri.elkitabi.features.namaz.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.core.utils.PrayerTimeUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamazVakitleriScreen(onBack: () -> Unit) {
    val vakitler = remember {
        PrayerTimeUtils.getPrayerTimes(latitude = 41.0082, longitude = 28.9784)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title          = { Text("Namaz Vakitleri") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            item {
                IslamicCard(modifier = Modifier.fillMaxWidth().padding(bottom = Spacing.md)) {
                    Column {
                        Text("İstanbul", style = MaterialTheme.typography.titleLarge, color = GoldAccent)
                        Text("Türkiye", style  = MaterialTheme.typography.bodySmall,  color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            items(vakitler) { vakit ->
                Row(
                    modifier              = Modifier.fillMaxWidth().padding(vertical = Spacing.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(vakit.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text  = PrayerTimeUtils.formatTime(vakit.time),
                        style = MaterialTheme.typography.titleMedium,
                        color = when {
                            vakit.isNext    -> GoldAccent
                            vakit.isCurrent -> IslamicGreen
                            else            -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
                HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            }
            item { Spacer(Modifier.height(Spacing.xxxl)) }
        }
    }
}
