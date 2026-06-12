package com.kadiri.elkitabi.features.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

private data class FeatureItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun FeatureGrid(
    onNavigateToKitap: () -> Unit,
    onNavigateToZikir: () -> Unit,
    onNavigateToDua: () -> Unit,
    onNavigateToNamaz: () -> Unit,
    onNavigateToKible: () -> Unit,
    onNavigateToEsma: () -> Unit,
    onNavigateToSilsile: () -> Unit,
    onNavigateToTakvim: () -> Unit,
    onNavigateToIlahi: () -> Unit,
    onNavigateToAmel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        FeatureItem("El Kitabı",    Icons.Filled.AutoStories,   onNavigateToKitap),
        FeatureItem("Zikirmatik",   Icons.Filled.Vibration,     onNavigateToZikir),
        FeatureItem("Dualar",       Icons.Filled.FavoriteBorder, onNavigateToDua),
        FeatureItem("Namaz",        Icons.Filled.Schedule,       onNavigateToNamaz),
        FeatureItem("Kıble",        Icons.Filled.Explore,        onNavigateToKible),
        FeatureItem("Esma'ül Hüsna",Icons.Filled.Star,           onNavigateToEsma),
        FeatureItem("Silsile",      Icons.Filled.PeopleAlt,      onNavigateToSilsile),
        FeatureItem("Takvim",       Icons.Filled.NightsStay,     onNavigateToTakvim),
        FeatureItem("İlahiler",     Icons.Filled.MusicNote,      onNavigateToIlahi),
        FeatureItem("Amel Defteri", Icons.Filled.TravelExplore,  onNavigateToAmel)
    )

    LazyVerticalGrid(
        columns        = GridCells.Fixed(4),
        modifier       = modifier.fillMaxWidth().height(260.dp),
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        verticalArrangement   = Arrangement.spacedBy(Spacing.sm),
        userScrollEnabled     = false
    ) {
        items(items) { item ->
            GlassCard(
                modifier = Modifier
                    .height(60.dp)
                    .clickable(onClick = item.onClick)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier            = Modifier.fillMaxWidth()
                ) {
                    Icon(item.icon, null, tint = GoldAccent, modifier = Modifier.size(22.dp))
                    Text(
                        text      = item.title,
                        style     = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        maxLines  = 1,
                        color     = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
