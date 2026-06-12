package com.kadiri.elkitabi.features.gunluk.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManeviGunlukScreen(
    onBack: () -> Unit,
    viewModel: ManeviGunlukViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📔 Kalbimin Defteri") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Filled.Edit, "Yeni Giriş")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, "Ekle")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(Modifier.height(4.dp)) }

            // Haftalık özet
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("HAFTALIK ÖZET", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            OzetItem("📅", "${state.toplamGun}", "Kayıt")
                            OzetItem("⭐", "%.1f".format(state.ortalamaRuhHali), "Ort. Puan")
                            OzetItem("📿", "${state.haftalikZikir}", "Zikir")
                        }
                    }
                }
            }

            // Yıllık ızgara
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("YILLIK GÖRÜNÜM", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                        Spacer(Modifier.height(8.dp))
                        ContributionGraph(entries = state.yillikVeri)
                    }
                }
            }

            item {
                Text("SON KAYITLAR", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
            }

            items(state.sonGunler) { entry ->
                GunlukKarti(entry = entry)
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }

    if (showDialog) {
        YeniGirisDialog(
            onDismiss = { showDialog = false },
            onKaydet = { sukur, tefekkur, puan ->
                viewModel.yeniGirisEkle(sukur, tefekkur, puan)
                showDialog = false
            }
        )
    }
}

@Composable
private fun OzetItem(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 24.sp)
        Text(value, fontWeight = FontWeight.Bold, color = GoldAccent)
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ContributionGraph(entries: List<Boolean>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(26),
        modifier = Modifier.height(80.dp),
        contentPadding = PaddingValues(0.dp),
        userScrollEnabled = false
    ) {
        items(entries.take(182)) { active ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .padding(1.dp)
                    .background(
                        if (active) IslamicGreen else MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.shapes.extraSmall
                    )
            )
        }
    }
}

@Composable
private fun GunlukKarti(entry: GunlukEntryUi) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(entry.tarih, style = MaterialTheme.typography.labelLarge, color = GoldAccent)
                Row {
                    Text(entry.ruhHaliEmoji, fontSize = 18.sp)
                    Spacer(Modifier.width(4.dp))
                    Text("${entry.puan}/10", style = MaterialTheme.typography.labelLarge)
                }
            }
            if (entry.sukur.isNotBlank()) {
                Text("🙏 ${entry.sukur}", style = MaterialTheme.typography.bodySmall)
            }
            if (entry.tefekkur.isNotBlank()) {
                Text("💭 ${entry.tefekkur}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun YeniGirisDialog(
    onDismiss: () -> Unit,
    onKaydet: (String, String, Int) -> Unit
) {
    var sukur by remember { mutableStateOf("") }
    var tefekkur by remember { mutableStateOf("") }
    var puan by remember { mutableStateOf(7f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Bugünkü Manevi Notlar") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = sukur,
                    onValueChange = { sukur = it },
                    label = { Text("Bugün ne için şükrediyorum?") },
                    minLines = 2
                )
                OutlinedTextField(
                    value = tefekkur,
                    onValueChange = { tefekkur = it },
                    label = { Text("Tefekkür notum") },
                    minLines = 2
                )
                Text("Manevi Hissiyat: ${puan.toInt()}/10", style = MaterialTheme.typography.labelLarge, color = GoldAccent)
                Slider(
                    value = puan,
                    onValueChange = { puan = it },
                    valueRange = 1f..10f,
                    steps = 8
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onKaydet(sukur, tefekkur, puan.toInt()) }) {
                Text("Kaydet")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("İptal") }
        }
    )
}
