package com.kadiri.elkitabi.features.mooddua.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodDuaScreen(
    onBack: () -> Unit,
    viewModel: MoodDuaViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kalbimin Hâline Göre Dua 💭") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (state.secilenMood != null) viewModel.moodTemizle()
                        else onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        AnimatedContent(
            targetState = state.secilenMood,
            modifier = Modifier.padding(padding),
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
            },
            label = "mood_transition"
        ) { secilenMood ->
            if (secilenMood == null) {
                MoodSecimEkrani(
                    moodlar = state.moodlar,
                    onMoodSec = viewModel::moodSec
                )
            } else {
                DuaListesiEkrani(
                    mood = secilenMood,
                    onGeri = viewModel::moodTemizle
                )
            }
        }
    }
}

@Composable
private fun MoodSecimEkrani(
    moodlar: List<MoodItem>,
    onMoodSec: (MoodItem) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Şu an nasıl hissediyorsunuz?",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            color = GoldAccent
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(moodlar) { mood ->
                MoodKarti(mood = mood, onClick = { onMoodSec(mood) })
            }
        }
    }
}

@Composable
private fun MoodKarti(mood: MoodItem, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = mood.emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = mood.isim,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun DuaListesiEkrani(
    mood: MoodItem,
    onGeri: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(text = mood.emoji, fontSize = 40.sp)
                Column {
                    Text(
                        text = mood.isim,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${mood.dualar.size + mood.ayetler.size} dua & ayet",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        if (mood.dualar.isNotEmpty()) {
            item {
                Text(
                    "DUALAR", style = MaterialTheme.typography.labelMedium,
                    color = GoldAccent
                )
            }
            items(mood.dualar) { dua ->
                DuaKarti(arabicText = dua.arabicText, turkishMeaning = dua.turkishMeaning, kaynak = dua.kaynak)
            }
        }
        if (mood.ayetler.isNotEmpty()) {
            item {
                Text(
                    "AYETLER", style = MaterialTheme.typography.labelMedium,
                    color = GoldAccent, modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(mood.ayetler) { ayet ->
                DuaKarti(arabicText = ayet.arabicText, turkishMeaning = ayet.turkishMeaning, kaynak = "${ayet.sure} ${ayet.ayet}")
            }
        }
    }
}

@Composable
private fun DuaKarti(arabicText: String, turkishMeaning: String, kaynak: String) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = arabicText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDirection = TextDirection.Rtl,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                color = GoldAccent
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Text(
                text = turkishMeaning,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "— $kaynak",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
