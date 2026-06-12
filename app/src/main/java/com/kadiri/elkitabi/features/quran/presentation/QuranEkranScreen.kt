package com.kadiri.elkitabi.features.quran.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranEkranScreen(
    onBack: () -> Unit,
    viewModel: QuranViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📖 Kuran Sureler") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            TabRow(selectedTabIndex = state.secilenTab) {
                Tab(selected = state.secilenTab == 0, onClick = { viewModel.tabDegistir(0) }, text = { Text("Öne Çıkan") })
                Tab(selected = state.secilenTab == 1, onClick = { viewModel.tabDegistir(1) }, text = { Text("Günün Ayeti") })
                Tab(selected = state.secilenTab == 2, onClick = { viewModel.tabDegistir(2) }, text = { Text("Hatim") })
            }

            when (state.secilenTab) {
                0 -> OneCikanSureler(state = state, onSureSec = viewModel::sureSec)
                1 -> GununAyeti(state = state)
                2 -> HatimTakip(state = state, onCuzIsaretle = viewModel::cuzIsaretle)
            }
        }
    }
}

@Composable
private fun OneCikanSureler(state: QuranUiState, onSureSec: (OncSure) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (state.secilenSure != null) {
            item {
                SureOkumaEkrani(sure = state.secilenSure)
            }
        } else {
            items(state.oncSureler) { sure ->
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(sure.isimTurkce, fontWeight = FontWeight.SemiBold)
                            Text("${sure.ayetSayisi} ayet", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(sure.isimArapca, fontSize = 20.sp, color = GoldAccent)
                            FilledTonalButton(
                                onClick = { onSureSec(sure) },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                modifier = Modifier.height(28.dp)
                            ) { Text("Oku", fontSize = 11.sp) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SureOkumaEkrani(sure: OncSure) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(sure.isimArapca, fontSize = 28.sp, color = GoldAccent)
                Text(sure.isimTurkce, style = MaterialTheme.typography.titleMedium)
            }
        }
        sure.ayetler.forEach { ayet ->
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = GoldAccent.copy(alpha = 0.2f)
                        ) {
                            Text("${ayet.numara}", modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    Text(
                        ayet.arapca,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDirection = TextDirection.Rtl,
                            fontSize = 22.sp,
                            lineHeight = 36.sp
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        color = GoldAccent
                    )
                    Text(ayet.turkce, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun GununAyeti(state: QuranUiState) {
    val ayet = state.gunAyeti
    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        if (ayet != null) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("✨ Günün Ayeti", style = MaterialTheme.typography.labelLarge, color = GoldAccent, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Text(
                        ayet.arapca,
                        style = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.Rtl, fontSize = 24.sp, lineHeight = 40.sp),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        color = GoldAccent
                    )
                    HorizontalDivider()
                    Text(ayet.turkce, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    Text(ayet.kaynak, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun HatimTakip(state: QuranUiState, onCuzIsaretle: (Int, Boolean) -> Unit) {
    val tamamlanan = state.cuzDurumu.count { it }
    Column(modifier = Modifier.padding(16.dp)) {
        GlassCard(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
            Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("$tamamlanan / 30", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                    Text("Tamamlanan Cüz", style = MaterialTheme.typography.labelSmall)
                }
                LinearProgressIndicator(
                    progress = { tamamlanan / 30f },
                    modifier = Modifier.width(120.dp).align(Alignment.CenterVertically)
                )
            }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(30) { idx ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${idx + 1}. Cüz", style = MaterialTheme.typography.bodyMedium)
                    Checkbox(
                        checked = state.cuzDurumu.getOrElse(idx) { false },
                        onCheckedChange = { onCuzIsaretle(idx, it) }
                    )
                }
            }
        }
    }
}
