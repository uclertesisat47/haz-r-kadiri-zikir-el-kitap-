package com.kadiri.elkitabi.features.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
    val onClick: () -> Unit,
    val yeni: Boolean = false
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
    onNavigateToBahce: () -> Unit = {},
    onNavigateToMoodDua: () -> Unit = {},
    onNavigateToRamazan: () -> Unit = {},
    onNavigateToGunluk: () -> Unit = {},
    onNavigateToQuiz: () -> Unit = {},
    onNavigateToPlanlayici: () -> Unit = {},
    onNavigateToKuran: () -> Unit = {},
    onNavigateToSiyer: () -> Unit = {},
    onNavigateToZekat: () -> Unit = {},
    onNavigateToVird: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val items = listOf(
        FeatureItem("El Kitabı",       Icons.Filled.AutoStories,    onNavigateToKitap),
        FeatureItem("Zikirmatik",      Icons.Filled.Vibration,      onNavigateToZikir),
        FeatureItem("Dualar",          Icons.Filled.FavoriteBorder, onNavigateToDua),
        FeatureItem("Namaz",           Icons.Filled.Schedule,       onNavigateToNamaz),
        FeatureItem("Kıble",           Icons.Filled.Explore,        onNavigateToKible),
        FeatureItem("Esmaül Hüsna",    Icons.Filled.Star,           onNavigateToEsma),
        FeatureItem("Silsile",         Icons.Filled.PeopleAlt,      onNavigateToSilsile),
        FeatureItem("Takvim",          Icons.Filled.NightsStay,     onNavigateToTakvim),
        FeatureItem("İlahiler",        Icons.Filled.MusicNote,      onNavigateToIlahi),
        FeatureItem("Amel Defteri",    Icons.Filled.TravelExplore,  onNavigateToAmel),
        FeatureItem("Hasenât Bahçesi", Icons.Filled.LocalFlorist,   onNavigateToBahce,   yeni = true),
        FeatureItem("Ruh Hali Duası",  Icons.Filled.Mood,           onNavigateToMoodDua, yeni = true),
        FeatureItem("Ramazan",         Icons.Filled.DarkMode,       onNavigateToRamazan, yeni = true),
        FeatureItem("Manevi Günlük",   Icons.Filled.Book,           onNavigateToGunluk,  yeni = true),
        FeatureItem("İlim Yarışması",  Icons.Filled.Quiz,           onNavigateToQuiz,    yeni = true),
        FeatureItem("İbadet Planı",    Icons.Filled.CalendarToday,  onNavigateToPlanlayici, yeni = true),
        FeatureItem("Kuran Sureler",   Icons.Filled.MenuBook,       onNavigateToKuran,   yeni = true),
        FeatureItem("Siyer",           Icons.Filled.HistoryEdu,     onNavigateToSiyer,   yeni = true),
        FeatureItem("Zekât Hesap",     Icons.Filled.Calculate,      onNavigateToZekat,   yeni = true),
        FeatureItem("Vird Rehberi",    Icons.Filled.SelfImprovement,onNavigateToVird,    yeni = true),
    )

    LazyVerticalGrid(
        columns        = GridCells.Fixed(4),
        modifier       = modifier
            .fillMaxWidth()
            .height(310.dp),
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        verticalArrangement   = Arrangement.spacedBy(Spacing.sm),
        userScrollEnabled     = false
    ) {
        items(items) { item ->
            GlassCard(
                modifier = Modifier
                    .height(70.dp)
                    .clickable(onClick = item.onClick)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier            = Modifier.fillMaxSize().padding(4.dp)
                    ) {
                        Icon(item.icon, null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                        Text(
                            text      = item.title,
                            style     = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            maxLines  = 2,
                            color     = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    if (item.yeni) {
                        Surface(
                            modifier = Modifier.align(Alignment.TopEnd).padding(2.dp),
                            color = GoldAccent,
                            shape = MaterialTheme.shapes.extraSmall
                        ) {
                            Text("Yeni", style = MaterialTheme.typography.labelSmall.copy(fontSize = androidx.compose.ui.unit.TextUnit.Unspecified), modifier = Modifier.padding(horizontal = 2.dp), color = androidx.compose.ui.graphics.Color.Black)
                        }
                    }
                }
            }
        }
    }
}
