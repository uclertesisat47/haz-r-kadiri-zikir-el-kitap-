package com.kadiri.elkitabi.features.siyer.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiyerScreen(
    onBack: () -> Unit,
    viewModel: SiyerViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var aramaMetni by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🕐 Siyer-i Nebevi") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = aramaMetni,
                    onValueChange = {
                        aramaMetni = it
                        viewModel.ara(it)
                    },
                    label = { Text("Olay veya yıl ara...") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            // Kategori filtreleri
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilterChip(
                        selected = state.secilenKategori == null,
                        onClick = { viewModel.kategoriFiltrele(null) },
                        label = { Text("Tümü", style = MaterialTheme.typography.labelSmall) }
                    )
                    state.kategoriler.take(3).forEach { kat ->
                        FilterChip(
                            selected = state.secilenKategori == kat.id,
                            onClick = { viewModel.kategoriFiltrele(kat.id) },
                            label = { Text(kat.isim, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
            }

            items(state.filtreliOlaylar) { olay ->
                SiyerOlayKarti(
                    olay = olay,
                    favori = state.favoriler.contains(olay.id),
                    onFavoriToggle = { viewModel.favoriToggle(olay.id) }
                )
            }

            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}

@Composable
private fun SiyerOlayKarti(
    olay: SiyerOlayUi,
    favori: Boolean,
    onFavoriToggle: () -> Unit
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = GoldAccent.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                olay.kategoriRenk,
                                fontSize = 8.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            "${olay.miladi} M.",
                            style = MaterialTheme.typography.labelMedium,
                            color = GoldAccent
                        )
                        if (olay.hicri != "-") {
                            Text(
                                "/ H. ${olay.hicri}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        olay.baslik,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                IconButton(onClick = onFavoriToggle) {
                    Icon(
                        if (favori) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint = if (favori) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(olay.ozet, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
