package com.kadiri.elkitabi.features.vird.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.kadiri.elkitabi.core.designsystem.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VirdRehberScreen(
    onBack: () -> Unit,
    viewModel: VirdRehberViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var secilenTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📿 Vird Rehberi", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            TabRow(selectedTabIndex = secilenTab) {
                Tab(selected = secilenTab == 0, onClick = { secilenTab = 0 }, text = { Text("☀️ Sabah Virdi") })
                Tab(selected = secilenTab == 1, onClick = { secilenTab = 1 }, text = { Text("🌙 Akşam Virdi") })
                Tab(selected = secilenTab == 2, onClick = { secilenTab = 2 }, text = { Text("📖 Hatim") })
            }

            when (secilenTab) {
                0 -> VirdListesi(
                    adimlar = state.sabahAdimlar,
                    aktifAdim = state.aktifAdim,
                    onAdimIlerle = viewModel::adimIlerle
                )
                1 -> VirdListesi(
                    adimlar = state.aksamAdimlar,
                    aktifAdim = state.aktifAdim,
                    onAdimIlerle = viewModel::adimIlerle
                )
                2 -> HatimListesi(adimlar = state.hatimAdimlar)
            }
        }
    }
}

@Composable
private fun VirdListesi(
    adimlar: List<VirdAdimUi>,
    aktifAdim: Int,
    onAdimIlerle: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            LinearProgressIndicator(
                progress = { if (adimlar.isEmpty()) 0f else (aktifAdim + 1) / adimlar.size.toFloat() },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
        }
        itemsIndexed(adimlar) { idx, adim ->
            VirdAdimKarti(
                adim = adim,
                index = idx,
                aktif = idx == aktifAdim,
                tamamlandi = idx < aktifAdim,
                onTamamla = { if (idx == aktifAdim) onAdimIlerle() }
            )
        }
        item { Spacer(Modifier.height(32.dp)) }
    }
}

@Composable
private fun VirdAdimKarti(
    adim: VirdAdimUi,
    index: Int,
    aktif: Boolean,
    tamamlandi: Boolean,
    onTamamla: () -> Unit
) {
    val renk = when {
        tamamlandi -> MaterialTheme.colorScheme.surfaceVariant
        aktif -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surface
    }
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = if (tamamlandi) IslamicGreen else if (aktif) GoldAccent else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            if (tamamlandi) "✓" else "${index + 1}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = if (tamamlandi || aktif) Color.Black else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(adim.baslik, fontWeight = if (aktif) FontWeight.Bold else FontWeight.Normal)
                }
                Text("×${adim.sayi}", style = MaterialTheme.typography.labelLarge, color = GoldAccent)
            }

            if (aktif) {
                Text(
                    adim.arabicText,
                    style = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.Rtl, fontSize = 18.sp),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                    color = GoldAccent
                )
                Text(adim.turkishMeaning, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(4.dp))
                Button(onClick = onTamamla, modifier = Modifier.fillMaxWidth()) {
                    Text("Tamamlandı ✓")
                }
            }
        }
    }
}

@Composable
private fun HatimListesi(adimlar: List<VirdAdimUi>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("HATM-İ HÂCEGÂN", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = GoldAccent)
                    Text("Kadiri tarikatının özel hatim rehberi", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        itemsIndexed(adimlar) { idx, adim ->
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("${idx + 1}. ${adim.baslik}", fontWeight = FontWeight.SemiBold)
                        Text("×${adim.sayi}", color = GoldAccent, fontWeight = FontWeight.Bold)
                    }
                    Text(adim.arabicText, style = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.Rtl), textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth(), color = GoldAccent.copy(alpha = 0.8f))
                    Text(adim.turkishMeaning, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        item { Spacer(Modifier.height(32.dp)) }
    }
}
