package com.kadiri.elkitabi.features.quiz.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizMenuScreen(
    onBack: () -> Unit,
    onKategoriSec: (String) -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("❓ İlim Yarışması") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            Text(
                "Kategori Seçin",
                style = MaterialTheme.typography.titleMedium,
                color = GoldAccent,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Her kategoride 10 soru cevaplayın",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.kategoriler) { kat ->
                    GlassCard(
                        modifier = Modifier
                            .height(100.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(kat.ikon, fontSize = 28.sp)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                kat.isim,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center
                            )
                            FilledTonalButton(
                                onClick = { onKategoriSec(kat.id) },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(24.dp).padding(top = 4.dp)
                            ) { Text("Başla", fontSize = 10.sp) }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("EN YÜKSEK SKORLAR", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
            Spacer(Modifier.height(8.dp))
            state.topSkorlar.forEach { skor ->
                GlassCard(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(skor.kategori, style = MaterialTheme.typography.bodySmall)
                        Text("${skor.dogru}/10 ✅", style = MaterialTheme.typography.bodySmall, color = GoldAccent)
                    }
                }
            }
        }
    }
}
