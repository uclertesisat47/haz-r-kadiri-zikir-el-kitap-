package com.kadiri.elkitabi.features.quiz.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    kategori: String,
    onBack: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(kategori) { viewModel.quizBaslat(kategori) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz — ${state.aktifKategori}") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        when {
            state.bitti -> SonucEkrani(state = state, onTekrar = { viewModel.quizBaslat(kategori) }, onBack = onBack)
            state.aktifSoru != null -> SoruEkrani(
                state = state,
                onCevapSec = viewModel::cevapSec,
                onSonraki = viewModel::sonrakiSoru,
                modifier = Modifier.padding(padding)
            )
            else -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun SoruEkrani(state: QuizUiState, onCevapSec: (Int) -> Unit, onSonraki: () -> Unit, modifier: Modifier) {
    val soru = state.aktifSoru ?: return
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LinearProgressIndicator(
            progress = { (state.soruIndex + 1) / state.toplamSoru.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "${state.soruIndex + 1} / ${state.toplamSoru}",
            style = MaterialTheme.typography.labelLarge,
            color = GoldAccent
        )
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = soru.soru,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
        soru.secenekler.forEachIndexed { idx, secenek ->
            val renk = when {
                state.secilenCevap == null -> MaterialTheme.colorScheme.surface
                idx == soru.dogruCevap -> Color(0xFF1B5E20)
                idx == state.secilenCevap -> Color(0xFFB71C1C)
                else -> MaterialTheme.colorScheme.surface
            }
            Surface(
                onClick = { if (state.secilenCevap == null) onCevapSec(idx) },
                modifier = Modifier.fillMaxWidth(),
                color = renk,
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${"ABCD"[idx]}. ", fontWeight = FontWeight.Bold, color = GoldAccent)
                    Text(secenek)
                }
            }
        }
        if (state.secilenCevap != null) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        if (state.secilenCevap == soru.dogruCevap) "✅ Doğru!" else "❌ Yanlış",
                        fontWeight = FontWeight.Bold,
                        color = if (state.secilenCevap == soru.dogruCevap) IslamicGreen else Color(0xFFEF5350)
                    )
                    Text(soru.aciklama, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 4.dp))
                    Button(onClick = onSonraki, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        Text(if (state.soruIndex + 1 < state.toplamSoru) "Sonraki >" else "Sonucu Gör")
                    }
                }
            }
        }
    }
}

@Composable
private fun SonucEkrani(state: QuizUiState, onTekrar: () -> Unit, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val puan = state.dogruSayisi
        Text(if (puan >= 8) "🏆" else if (puan >= 5) "⭐" else "📚", fontSize = 72.sp)
        Spacer(Modifier.height(16.dp))
        Text("$puan / ${state.toplamSoru}", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
        Text("Doğru Cevap", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Text(
            when {
                puan >= 9 -> "Mâşâallah! Mükemmel bilgi!"
                puan >= 7 -> "Çok iyi! Devam edin."
                puan >= 5 -> "İyi, biraz daha çalışın."
                else -> "Tekrar çalışmanızı öneririm."
            },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onBack) { Text("Menüye Dön") }
            Button(onClick = onTekrar) { Text("Tekrar Oyna") }
        }
    }
}
